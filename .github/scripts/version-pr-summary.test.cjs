const assert = require('node:assert/strict');
const summarize = require('./version-pr-summary.cjs');

const repository = 'shopl/shopl-design-guide-android';
const versionPr = {
  number: 459, state: 'open', body: '- #458 \r\n- #457',
  base: { ref: 'main', sha: 'improvement' }, mergeable: true, merge_commit_sha: 'merge-result',
  head: { ref: 'version/scott/minor-1.23.0', sha: 'version-head', repo: { full_name: repository } },
};
const pull = (number, sha, labels = [], overrides = {}) => ({
  number, title: `작업 ${number}`, merged_at: `2026-10-02T06:${number % 60}:00Z`,
  merge_commit_sha: sha, base: { ref: 'main' }, head: { ref: `update/scott/task-${number}` },
  labels: labels.map(name => ({ name })), ...overrides,
});

async function run({ labels = ['add'], body = versionPr.body, action, total = 3, divergent = false, headChanged = false, baseChanged = false, conflict = false, concurrentBody, fail = false } = {}) {
  const updates = [];
  const pages = [];
  const requests = [];
  let reads = 0;
  const pr = { ...versionPr, body, mergeable: !conflict, base: { ref: 'main', sha: total ? 'improvement' : 'previous-release' } };
  const feature = pull(458, 'feature', labels);
  const improvement = pull(457, 'improvement', ['update']);
  const github = {
    rest: {
      repos: { getLatestRelease: async () => ({ data: { tag_name: 'v1.22.9', html_url: 'https://example.test/v1.22.9' } }) },
      pulls: {
        list: 'list-pulls',
        get: async () => {
          reads++;
          return { data: { ...pr, body: reads > 1 ? (concurrentBody ?? body) : body,
            base: { ...pr.base, sha: reads > 1 && baseChanged ? 'new-base' : pr.base.sha },
            head: { ...pr.head, sha: reads > 1 && headChanged ? 'new-head' : pr.head.sha } } };
        },
        update: async update => { updates.push(update); },
      },
    },
    request: async (route, params) => {
      assert.equal(route, 'GET /repos/{owner}/{repo}/compare/{basehead}');
      assert.equal(params.basehead, 'v1.22.9...merge-result');
      pages.push(params.page);
      const commits = Array.from({ length: total }, (_, i) => ({ sha: ['feature', 'improvement', 'version-head'][i] || `commit-${i}` }));
      return { data: { status: divergent ? 'diverged' : 'ahead', base_commit: { sha: 'previous-release' },
        merge_base_commit: { sha: 'previous-release' }, total_commits: total,
        commits: commits.slice((params.page - 1) * 100, params.page * 100) } };
    },
    paginate: async (route, params) => {
      if (route === 'list-pulls') return [versionPr];
      requests.push(params.commit_sha);
      if (fail) throw new Error('GitHub API failure');
      if (params.commit_sha === 'feature') return [
        feature, feature,
        pull(100, 'before-release', ['add']),
        pull(101, 'feature', ['add'], { base: { ref: 'other' } }),
        pull(102, 'feature', ['add'], { merged_at: null }),
        pull(103, 'feature', ['add'], { head: { ref: 'version/scott/minor-1.22.0' } }),
      ];
      if (params.commit_sha === 'improvement') return [improvement];
      if (params.commit_sha === 'version-head') return [versionPr];
      return [];
    },
  };
  const context = { repo: { owner: 'shopl', repo: 'shopl-design-guide-android' }, eventName: 'pull_request',
    payload: { action: action || 'opened', pull_request: action ? { ...feature, merged: true } : versionPr } };
  await summarize({ github, context });
  return { updates, pages, requests };
}

(async () => {
  const minor = await run();
  assert.equal(minor.updates.length, 1);
  const body = minor.updates[0].body;
  assert.match(body, /권장 버전: \*\*1\.23\.0\*\* \(`minor`\)/);
  assert.match(body, /#458 \*\*add\*\*/);
  assert.match(body, /#457 \*\*update\*\*/);
  assert.equal(body.match(/#458/g).length, 1);
  assert.doesNotMatch(body, /#100|#101|#102|#103|#459/);

  const patch = await run({ labels: ['update', 'document'] });
  assert.match(patch.updates[0].body, /권장 버전: \*\*1\.22\.10\*\* \(`patch`\)/);
  assert.equal((await run({ body })).updates.length, 0);

  const notes = await run({ body: `수동 메모\n\n${body}\n\n리뷰 요청`, labels: [] });
  assert.match(notes.updates[0].body, /^수동 메모\n\n/);
  assert.match(notes.updates[0].body, /\n\n리뷰 요청$/);
  assert.match(notes.updates[0].body, /#458 \*\*라벨 없음\*\*/);
  assert.equal(notes.updates[0].body.match(/sdg-version-summary:start/g).length, 1);
  assert.match((await run({ body: '수동 메모' })).updates[0].body, /^수동 메모\n\n/);
  assert.match((await run({ concurrentBody: '조회 중 추가한 메모' })).updates[0].body, /^조회 중 추가한 메모\n\n/);
  assert.equal((await run({ headChanged: true })).updates.length, 0);
  assert.equal((await run({ baseChanged: true })).updates.length, 0);

  const paginated = await run({ total: 251 });
  assert.deepEqual(paginated.pages, [1, 2, 3]);
  assert.equal(paginated.requests.length, 251);
  assert.equal((await run({ action: 'labeled' })).updates.length, 1);
  assert.equal((await run({ action: 'closed' })).updates.length, 1);
  assert.match((await run({ total: 0 })).updates[0].body, /포함된 변경 PR이 없습니다/);
  await assert.rejects(run({ divergent: true }), /기준 커밋/);
  await assert.rejects(run({ conflict: true }), /머지 가능 여부/);
  await assert.rejects(run({ fail: true }), /GitHub API failure/);
  await assert.rejects(run({ body: '<!-- sdg-version-summary:start -->' }), /시작\/끝/);
  console.log('version-pr-summary checks passed');
})().catch(error => { console.error(error); process.exitCode = 1; });
