const assert = require('node:assert/strict');
const createVersionPr = require('./create-version-pr.cjs');
const versionPath = 'build-logic/src/main/kotlin/com/shopl/sdg/build_logic/PublishingConfig.kt';
const repository = 'shopl/shopl-design-guide-android';
const encode = content => ({ type: 'file', encoding: 'base64', content: Buffer.from(content).toString('base64') });
const context = { repo: { owner: 'shopl', repo: 'shopl-design-guide-android' }, ref: 'refs/heads/main' };
const original = 'object PublishingConfig {\n    const val VERSION = "1.22.0"\n}\n';
const existingPr = { number: 459, html_url: 'https://example.test/459', labels: [],
  head: { ref: 'version/scott/minor-1.23.0', repo: { full_name: repository } } };

async function run({ add = true, existing = false, concurrent = false, empty = false, mainChanged = false,
  releaseChanged = false, currentVersion = '1.22.0', branchExists = false, branchChanged = false, wrongRef = false,
  fail = false, missingLabel = false, dryRun = false, assignee = '', assigneeId = '', requesterSlackId = '',
  accountLogin = 'Songgyubin', accountMissing = false, wrongAccount = false,
  unassignable = false, assignmentIgnored = false, assignmentFailure = false, assignmentWrongId = false, writes = [] } = {}) {
  let mainReads = 0;
  let releaseReads = 0;
  let listReads = 0;
  const nextVersion = add ? '1.23.0' : '1.22.1';
  const bump = add ? 'minor' : 'patch';
  const branch = `version/automation/${bump}-${nextVersion}`;
  const github = {
    rest: {
      pulls: {
        list: 'pulls',
        create: async params => { writes.push(['pr', params]); return { data: { number: 461, html_url: 'https://example.test/461' } }; },
        requestReviewers: async params => { writes.push(['reviewers', params]); },
      },
      issues: {
        addLabels: async params => { writes.push(['labels', params]); },
        addAssignees: async params => {
          writes.push(['assignee', params]);
          if (assignmentFailure) throw new Error('assignee API failure');
          return { data: { assignees: assignmentIgnored ? [] : params.assignees.map(login => ({ login: login.toLowerCase(),
            id: assignmentWrongId ? 999 : Number(assigneeId) })) } };
        },
      },
      repos: {
        getLatestRelease: async () => ({ data: { tag_name: ++releaseReads > 1 && releaseChanged ? 'v1.23.0' : 'v1.22.0', html_url: 'https://example.test/v1.22.0' } }),
        getContent: async ({ path, ref }) => {
          assert.equal(path, versionPath);
          return { data: encode(ref === 'branch-sha' ? original.replace('1.22.0', nextVersion) : original.replace('1.22.0', currentVersion)) };
        },
      },
      git: {
        getRef: async ({ ref }) => {
          if (ref === 'heads/main') return { data: { object: { sha: ++mainReads > 1 && mainChanged ? 'new-main' : 'main-sha' } } };
          assert.equal(ref, `heads/${branch}`);
          if (!branchExists) throw Object.assign(new Error('not found'), { status: 404 });
          return { data: { object: { sha: 'branch-sha' } } };
        },
        getCommit: async ({ commit_sha }) => { assert.equal(commit_sha, 'main-sha'); return { data: { tree: { sha: 'main-tree' } } }; },
        createBlob: async params => { writes.push(['blob', params]); return { data: { sha: 'version-blob' } }; },
        createTree: async params => { writes.push(['tree', params]); return { data: { sha: 'version-tree' } }; },
        createCommit: async params => { writes.push(['commit', params]); return { data: { sha: 'version-commit' } }; },
        createRef: async params => { writes.push(['ref', params]); },
      },
    },
    request: async (route, { basehead, assignee: login, account_id }) => {
      if (route === 'GET /user/{account_id}') {
        assert.equal(account_id, Number(assigneeId));
        assert.equal(writes.length, 0);
        if (accountMissing) throw Object.assign(new Error('missing user'), { status: 404 });
        return { data: { id: wrongAccount ? 999 : account_id, login: accountLogin, type: 'User' } };
      }
      if (route === 'GET /repos/{owner}/{repo}/assignees/{assignee}') {
        assert.equal(login, assigneeId ? accountLogin : assignee);
        assert.equal(writes.length, 0);
        if (unassignable) throw Object.assign(new Error('not assignable'), { status: 404 });
        return { status: 204 };
      }
      if (fail) throw new Error('GitHub API failure');
      if (basehead.endsWith('branch-sha')) return { data: { status: 'ahead', ahead_by: 1, files: [{ filename: branchChanged ? 'other.kt' : versionPath }] } };
      assert.equal(basehead, 'v1.22.0...main-sha');
      return { data: { status: empty ? 'identical' : 'ahead', base_commit: { sha: 'release-sha' },
        merge_base_commit: { sha: 'release-sha' }, total_commits: empty ? 0 : 1, commits: empty ? [] : [{ sha: 'main-sha' }] } };
    },
    paginate: async route => {
      if (route === 'pulls') {
        listReads++;
        if (existing || (concurrent && listReads > 1)) return [{ ...existingPr,
          head: { ...existingPr.head, ref: missingLabel ? branch : existingPr.head.ref } }];
        return [{ ...existingPr, head: { ...existingPr.head, repo: null } }];
      }
      return [{ number: 458, merged_at: '2026-10-02T06:00:00Z', merge_commit_sha: 'main-sha',
        base: { ref: 'main' }, head: { ref: 'add/scott/task' }, labels: [{ name: add ? 'add' : 'update' }] }];
    },
  };
  const result = await createVersionPr({ github, context: { ...context, ref: wrongRef ? 'refs/heads/other' : context.ref },
    dryRun, assignee, assigneeId, requesterSlackId });
  return { result, writes };
}

(async () => {
  const minor = await run();
  assert.equal(minor.result.version, '1.23.0');
  assert.deepEqual(minor.writes.map(([kind]) => kind), ['blob', 'tree', 'commit', 'ref', 'pr', 'labels', 'reviewers']);
  assert.match(minor.writes[0][1].content, /VERSION = "1.23.0"/);
  assert.deepEqual(minor.writes[1][1].tree.map(file => file.path), [versionPath]);
  assert.equal(minor.writes[4][1].head, 'version/automation/minor-1.23.0');
  assert.match(minor.writes[4][1].body, /^- #458 \*\*add\*\*$/m);
  assert.deepEqual(minor.writes[5][1].labels, ['minor']);
  const assigned = await run({ assignee: 'Songgyubin', requesterSlackId: 'UTEST' });
  assert.equal(assigned.result.assignee, 'Songgyubin');
  assert.deepEqual(assigned.writes.map(([kind]) => kind), ['blob', 'tree', 'commit', 'ref', 'pr', 'assignee', 'labels', 'reviewers']);
  assert.deepEqual(assigned.writes[5][1], { owner: 'shopl', repo: 'shopl-design-guide-android', issue_number: 461, assignees: ['Songgyubin'] });
  const linked = await run({ assigneeId: '37494776', requesterSlackId: 'UTEST' });
  assert.equal(linked.result.assignee, 'Songgyubin');
  assert.deepEqual(linked.writes[5][1].assignees, ['Songgyubin']);
  await assert.rejects(run({ assigneeId: '37494776', assignmentWrongId: true }), /담당자 지정에 실패/);
  const renamed = await run({ assignee: 'old-login', assigneeId: '37494776', accountLogin: 'new-login', dryRun: true });
  assert.equal(renamed.result.assignee, 'new-login');
  assert.equal(renamed.writes.length, 0);
  assert.equal((await run({ assigneeId: '37494776', existing: true })).writes.length, 0);
  for (const assigneeId of ['0', '-1', 'bad', '9007199254740992', 123]) {
    await assert.rejects(run({ assigneeId }), /담당자 ID 형식/);
  }
  for (const args of [{ accountMissing: true }, { wrongAccount: true }, { accountLogin: 'bad<@UTEST>' }]) {
    const accountWrites = [];
    await assert.rejects(run({ assigneeId: '37494776', writes: accountWrites, ...args }), /GitHub/);
    assert.equal(accountWrites.length, 0);
  }
  assert.equal((await run({ assignee: 'other-member', existing: true })).writes.length, 0);
  assert.equal((await run({ assignee: 'other-member', concurrent: true })).writes.length, 0);
  assert.equal((await run({ assignee: 'Songgyubin', empty: true })).writes.length, 0);
  const assignmentPreview = await run({ assignee: 'Songgyubin', requesterSlackId: 'UTEST', dryRun: true });
  assert.equal(assignmentPreview.result.assignee, 'Songgyubin');
  assert.equal(assignmentPreview.writes.length, 0);
  assert.deepEqual((await run({ assignee: 'Songgyubin', branchExists: true })).writes.map(([kind]) => kind), ['pr', 'assignee', 'labels', 'reviewers']);
  const blockedWrites = [];
  await assert.rejects(run({ assignee: 'unknown-member', unassignable: true, writes: blockedWrites }), /지정할 수 없습니다/);
  assert.equal(blockedWrites.length, 0);
  await assert.rejects(run({ assignee: 'Songgyubin', assignmentIgnored: true }), /담당자 지정에 실패/);
  await assert.rejects(run({ assignee: 'Songgyubin', assignmentFailure: true }), /assignee API failure/);
  await assert.rejects(run({ assignee: 'bad<@UTEST>' }), /계정 형식/);
  await assert.rejects(run({ requesterSlackId: 'UTEST' }), /요청자와 GitHub 담당자/);
  await assert.rejects(run({ requesterSlackId: '<@UOTHER>', assignee: 'Songgyubin' }), /요청자와 GitHub 담당자/);
  assert.equal((await run({ add: false })).result.version, '1.22.1');
  assert.equal((await run({ existing: true })).writes.length, 0);
  assert.equal((await run({ concurrent: true })).writes.length, 0);
  assert.equal((await run({ empty: true })).result.status, 'empty');
  assert.equal((await run({ empty: true })).writes.length, 0);
  assert.deepEqual((await run({ branchExists: true })).writes.map(([kind]) => kind), ['pr', 'labels', 'reviewers']);
  assert.deepEqual((await run({ existing: true, missingLabel: true })).writes.map(([kind]) => kind), ['labels']);
  for (const add of [true, false]) {
    const preview = await run({ dryRun: true, add });
    assert.equal(preview.result.status, 'preview');
    assert.equal(preview.result.version, add ? '1.23.0' : '1.22.1');
    assert.equal(preview.result.path, versionPath);
    assert.match(preview.result.content, new RegExp(`VERSION = "${preview.result.version}"`));
    assert.equal(preview.writes.length, 0);
  }
  assert.equal((await run({ dryRun: true, existing: true, missingLabel: true })).writes.length, 0);
  assert.equal((await run({ dryRun: true, concurrent: true, missingLabel: true })).writes.length, 0);
  assert.equal((await run({ dryRun: true, branchExists: true })).writes.length, 0);
  assert.equal((await run({ dryRun: true, empty: true })).writes.length, 0);
  await assert.rejects(run({ dryRun: true, mainChanged: true }), /조회 중/);
  await assert.rejects(run({ dryRun: true, currentVersion: '1.23.0' }), /진행 중인 릴리스/);
  await assert.rejects(run({ mainChanged: true }), /조회 중/);
  await assert.rejects(run({ releaseChanged: true }), /조회 중/);
  await assert.rejects(run({ currentVersion: '1.23.0' }), /진행 중인 릴리스/);
  await assert.rejects(run({ branchExists: true, branchChanged: true }), /다른 변경/);
  await assert.rejects(run({ wrongRef: true }), /main/);
  await assert.rejects(run({ fail: true }), /GitHub API failure/);
  console.log('create-version-pr checks passed');
})().catch(error => { console.error(error); process.exitCode = 1; });
