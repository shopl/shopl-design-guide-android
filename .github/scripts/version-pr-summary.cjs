const startMarker = '<!-- sdg-version-summary:start -->';
const endMarker = '<!-- sdg-version-summary:end -->';
const versionPath = 'build-logic/src/main/kotlin/com/shopl/sdg/build_logic/PublishingConfig.kt';

async function getPublishingConfig({ github, context, ref }) {
  const { data: file } = await github.rest.repos.getContent({ ...context.repo, path: versionPath, ref });
  if (file.type !== 'file' || file.encoding !== 'base64') throw new Error('버전 설정 파일을 확인해주세요.');
  const content = Buffer.from(file.content, 'base64').toString('utf8');
  const versions = [...content.matchAll(/\bconst val VERSION = "([^"]+)"/g)];
  if (versions.length !== 1) throw new Error('버전 설정 파일에 VERSION이 하나인지 확인해주세요.');
  return { content, version: versions[0][1] };
}

function mergeSummary(body, summary) {
  const start = body.indexOf(startMarker);
  const end = body.indexOf(endMarker);
  if (start !== -1 || end !== -1) {
    if (start === -1 || end < start || body.split(startMarker).length !== 2 || body.split(endMarker).length !== 2) {
      throw new Error('자동 생성 영역의 시작/끝 표시를 확인해주세요.');
    }
    return body.slice(0, start) + summary + body.slice(end + endMarker.length);
  }

  const text = body.trim();
  const onlyPrNumbers = /^(?:[ \t]*-[ \t]+#\d+[ \t]*(?:\r?\n|$))+$/.test(text);
  return !text || onlyPrNumbers ? summary : `${body}\n\n${summary}`;
}

const escapeMarkdown = text => text.replace(/\r?\n/g, ' ').replace(/[\\`*_{}\[\]<>()#!|@]/g, '\\$&');
const getSummaryLabels = pull => pull.head.ref === 'update/icon' ? ['icon'] : pull.labels.map(label => label.name);

async function getVersionChanges({ github, context, release, headSha, excludePullNumber }) {
  const { owner, repo } = context.repo;
  const version = release.tag_name.match(/^v?(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)$/);
  if (!version) throw new Error(`이전 릴리스 태그가 정식 버전 형식이 아닙니다: ${release.tag_name}`);
  const commits = [];
  let releaseSha;
  for (let page = 1; ; page++) {
    const { data: comparison } = await github.request('GET /repos/{owner}/{repo}/compare/{basehead}', {
      owner, repo, basehead: `${release.tag_name}...${headSha}`, per_page: 100, page,
    });
    if (!['ahead', 'identical'].includes(comparison.status) || comparison.base_commit.sha !== comparison.merge_base_commit.sha) {
      throw new Error(`${release.tag_name} 태그가 기준 커밋에 포함되지 않습니다.`);
    }
    releaseSha = comparison.base_commit.sha;
    commits.push(...comparison.commits);
    if (commits.length >= comparison.total_commits) break;
    if (!comparison.commits.length) throw new Error('커밋 목록을 모두 조회하지 못했습니다.');
  }

  const commitShas = new Set(commits.map(commit => commit.sha));
  const included = new Map();
  for (const commit of commits) {
    const associated = await github.paginate('GET /repos/{owner}/{repo}/commits/{commit_sha}/pulls', {
      owner, repo, commit_sha: commit.sha, per_page: 100,
    });
    for (const pull of associated) {
      if (pull.merged_at && pull.base.ref === 'main' && commitShas.has(pull.merge_commit_sha) &&
          pull.number !== excludePullNumber && !pull.head.ref.startsWith('version/')) {
        included.set(pull.number, pull);
      }
    }
  }

  const pulls = [...included.values()].sort((a, b) => b.merged_at.localeCompare(a.merged_at) || b.number - a.number);
  const addCount = pulls.filter(pull => getSummaryLabels(pull).includes('add')).length;
  const bump = addCount ? 'minor' : 'patch';
  const [major, minor, patch] = version.slice(1).map(Number);
  const nextVersion = addCount ? `${major}.${minor + 1}.0` : `${major}.${minor}.${patch + 1}`;
  const changes = pulls.map(pull => {
    const labels = getSummaryLabels(pull).map(label => `**${escapeMarkdown(label)}**`).join(' ') || '**라벨 없음**';
    return `- #${pull.number} ${labels}`;
  });
  const body = [
    startMarker,
    '## 버전 변경 요약',
    `- 이전 릴리스: [${release.tag_name}](${release.html_url})`,
    `- 변경 버전: **${nextVersion}** (\`${bump}\`)`,
    `- 판정 근거: \`add\` 라벨 PR ${addCount}개 → \`${bump}\``,
    '',
    '## 포함된 PR',
    ...(changes.length ? changes : ['- 포함된 변경 PR이 없습니다.']),
    '',
    endMarker,
  ].join('\n');
  return { body, pulls, bump, nextVersion, releaseSha, commitShas };
}

module.exports = async ({ github, context }) => {
  const { owner, repo } = context.repo;
  const eventPull = context.payload.pull_request;
  let numbers = [];
  if (context.eventName === 'workflow_dispatch') {
    const number = Number(context.payload.inputs.pr_number);
    if (!Number.isSafeInteger(number) || number < 1) throw new Error('유효한 PR 번호를 입력해주세요.');
    numbers = [number];
  } else if (eventPull?.head.ref.startsWith('version/') && context.payload.action !== 'closed') {
    numbers = [eventPull.number];
  } else if (['labeled', 'unlabeled'].includes(context.payload.action) || (context.payload.action === 'closed' && eventPull.merged)) {
    const openPulls = await github.paginate(github.rest.pulls.list, { owner, repo, state: 'open', base: 'main', per_page: 100 });
    numbers = openPulls.filter(pr => pr.head.ref.startsWith('version/')).map(pr => pr.number);
  }
  if (!numbers.length) return;

  const { data: release } = await github.rest.repos.getLatestRelease({ owner, repo });

  for (const number of numbers) {
    let { data: pr } = await github.rest.pulls.get({ owner, repo, pull_number: number });
    if (pr.state !== 'open' || pr.base.ref !== 'main' || !pr.head.ref.startsWith('version/') || pr.head.repo?.full_name !== `${owner}/${repo}`) continue;

    for (let attempt = 0; pr.mergeable === null && attempt < 3; attempt++) {
      await new Promise(resolve => setTimeout(resolve, 2000));
      ({ data: pr } = await github.rest.pulls.get({ owner, repo, pull_number: number }));
    }
    if (pr.state !== 'open') continue;
    if (!pr.mergeable || !pr.merge_commit_sha) throw new Error(`#${number}의 머지 가능 여부를 확인한 뒤 다시 실행해주세요.`);

    const { body: summary, nextVersion, releaseSha, commitShas } = await getVersionChanges({
      github, context, release, headSha: pr.merge_commit_sha, excludePullNumber: number,
    });
    if (pr.base.sha !== releaseSha && !commitShas.has(pr.base.sha)) throw new Error('GitHub의 머지 결과에 최신 main이 반영된 뒤 다시 실행해주세요.');
    const { version } = await getPublishingConfig({ github, context, ref: pr.head.sha });

    // API 조회 중 작성한 메모를 보존하고, 변경된 머지 결과에는 이전 결과를 쓰지 않습니다.
    const { data: current } = await github.rest.pulls.get({ owner, repo, pull_number: number });
    if (current.state !== 'open' || current.head.sha !== pr.head.sha || current.base.ref !== pr.base.ref || current.base.sha !== pr.base.sha || current.merge_commit_sha !== pr.merge_commit_sha) continue;
    // 다른 PR의 병합·라벨 변경으로 실행돼도 결과는 버전 PR에 표시합니다.
    await github.rest.repos.createCommitStatus({ owner, repo, sha: pr.head.sha,
      context: 'SDG 버전 검증', state: version === nextVersion ? 'success' : 'failure',
      description: version === nextVersion ? `VERSION ${version} 검증 완료` : `VERSION ${version} · 필요한 버전 ${nextVersion}`,
      ...(context.runId ? { target_url: `${context.serverUrl}/${owner}/${repo}/actions/runs/${context.runId}` } : {}),
    });
    if (version !== nextVersion) {
      throw new Error(`#${number}의 VERSION은 ${version}이지만 필요한 버전은 ${nextVersion}입니다. 버전 PR을 수정한 뒤 다시 실행해주세요.`);
    }
    const body = mergeSummary(current.body || '', summary);
    if (body !== (current.body || '')) {
      await github.rest.pulls.update({ owner, repo, pull_number: number, body });
    }
  }
};

module.exports.getVersionChanges = getVersionChanges;
module.exports.getPublishingConfig = getPublishingConfig;
module.exports.versionPath = versionPath;
