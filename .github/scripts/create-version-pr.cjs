const { getVersionChanges, getPublishingConfig, versionPath } = require('./version-pr-summary.cjs');
const requestMarker = '<!-- sdg-version-request:';

async function resolveAssignee(github, { assignee, assigneeId }) {
  if (assignee && (typeof assignee !== 'string' || !/^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(assignee))) {
    throw new Error('GitHub 담당자 계정 형식을 확인해주세요.');
  }
  if (assigneeId) {
    if (typeof assigneeId !== 'string' || !/^[1-9]\d*$/.test(assigneeId) || !Number.isSafeInteger(Number(assigneeId))) {
      throw new Error('GitHub 담당자 ID 형식을 확인해주세요.');
    }
    let account;
    try {
      ({ data: account } = await github.request('GET /user/{account_id}', { account_id: Number(assigneeId) }));
    } catch (error) {
      if (error.status !== 404) throw error;
      throw new Error('토큰 소유자의 GitHub 계정을 찾을 수 없습니다. 토큰을 확인해주세요.');
    }
    if (account.id !== Number(assigneeId) || account.type !== 'User' ||
        typeof account.login !== 'string' || !/^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(account.login)) {
      throw new Error('토큰 소유자의 GitHub 담당자 계정을 확인하지 못했습니다.');
    }
    assignee = account.login;
  }
  return assignee;
}

module.exports = async ({ github, context, dryRun = false, assignee = '', assigneeId = '', requesterSlackId = '', branchAuthor = '' }) => {
  const { owner, repo } = context.repo;
  if (context.ref !== 'refs/heads/main') throw new Error('버전 PR 생성은 main에서 실행해주세요.');
  if (typeof branchAuthor !== 'string' || !/^[a-z][a-z0-9-]{0,31}$/.test(branchAuthor)) {
    throw new Error('브랜치 작성자 이름은 영문 소문자로 시작하고 소문자·숫자·하이픈으로 32자 이내로 입력해주세요.');
  }
  if (requesterSlackId && (!/^[UW][A-Z0-9]+$/.test(requesterSlackId) || (!assignee && !assigneeId))) {
    throw new Error('Slack 요청자와 GitHub 담당자 설정을 확인해주세요.');
  }
  assignee = await resolveAssignee(github, { assignee, assigneeId });

  const checkAssignee = async login => {
    if (!login) return;
    try {
      await github.request('GET /repos/{owner}/{repo}/assignees/{assignee}', { owner, repo, assignee: login });
    } catch (error) {
      if (error.status !== 404) throw error;
      throw new Error(`${login} 계정을 이 저장소의 담당자로 지정할 수 없습니다.`);
    }
  };
  const ensureMetadata = async (pr, request, bump) => {
    if (request.assignee && !pr.assignees?.length) {
      const { data: assigned } = await github.rest.issues.addAssignees({ owner, repo,
        issue_number: pr.number, assignees: [request.assignee] });
      if (!assigned.assignees.some(user => user.login.toLowerCase() === request.assignee.toLowerCase() &&
          (!request.assigneeId || user.id === Number(request.assigneeId)))) {
        throw new Error(`${pr.html_url}의 담당자 지정에 실패했습니다. GitHub 쓰기 권한을 확인해주세요.`);
      }
    }
    if (!pr.labels.some(label => label.name === bump)) {
      await github.rest.issues.addLabels({ owner, repo, issue_number: pr.number, labels: [bump] });
    }
    if (pr.requested_teams?.length || pr.requested_reviewers?.length) return;
    const reviews = await github.paginate(github.rest.pulls.listReviews, { owner, repo, pull_number: pr.number, per_page: 100 });
    if (!reviews.some(review => review.submitted_at)) {
      await github.rest.pulls.requestReviewers({ owner, repo, pull_number: pr.number, team_reviewers: ['android'] });
    }
  };

  const findOpenVersionPr = async () => (await github.paginate(github.rest.pulls.list, {
    owner, repo, state: 'open', base: 'main', per_page: 100,
  })).find(pr => pr.head.ref.startsWith('version/') && pr.head.repo?.full_name === `${owner}/${repo}`);
  const reuse = async pr => {
    if (dryRun) return { status: 'existing', url: pr.html_url, number: pr.number };
    ({ data: pr } = await github.rest.pulls.get({ owner, repo, pull_number: pr.number }));
    if (pr.state !== 'open') throw new Error('기존 버전 PR이 닫혔습니다. 다시 실행해주세요.');
    const versionBranch = pr.head.ref.match(/^version\/[^/]+\/(minor|patch)-\d+\.\d+\.\d+$/);
    const body = pr.body || '';
    if (versionBranch && body.includes(requestMarker)) {
      const markers = [...body.matchAll(/<!-- sdg-version-request:\s*([\s\S]*?)\s*-->/g)];
      let request;
      try {
        if (markers.length !== 1 || body.split(requestMarker).length !== 2) throw new Error();
        request = JSON.parse(markers[0][1]);
        if (typeof request?.assignee !== 'string' || typeof request?.assigneeId !== 'string') throw new Error();
      } catch {
        throw new Error('버전 PR에 저장된 최초 요청자 정보를 확인해주세요.');
      }
      if (!pr.assignees?.length) {
        request.assignee = await resolveAssignee(github, request);
        await checkAssignee(request.assignee);
      }
      await ensureMetadata(pr, request, versionBranch[1]);
    } else if (versionBranch && !pr.labels.some(label => label.name === versionBranch[1])) {
      await github.rest.issues.addLabels({ owner, repo, issue_number: pr.number, labels: [versionBranch[1]] });
    }
    return { status: 'existing', url: pr.html_url, number: pr.number };
  };
  const existing = await findOpenVersionPr();
  if (existing) return reuse(existing);

  const { data: main } = await github.rest.git.getRef({ owner, repo, ref: 'heads/main' });
  const mainSha = main.object.sha;
  const { data: release } = await github.rest.repos.getLatestRelease({ owner, repo });
  const changes = await getVersionChanges({ github, context, release, headSha: mainSha });
  if (!changes.commitShas.size) return { status: 'empty', ...(dryRun ? { body: changes.body } : {}) };

  const { content, version } = await getPublishingConfig({ github, context, ref: mainSha });
  if (version !== release.tag_name.replace(/^v/, '')) {
    throw new Error('main의 VERSION과 이전 릴리스 버전이 다릅니다. 진행 중인 릴리스를 확인해주세요.');
  }
  const updated = content.replace(/\bconst val VERSION = "[^"]+"/, `const val VERSION = "${changes.nextVersion}"`);
  const branch = `version/${branchAuthor}/${changes.bump}-${changes.nextVersion}`;
  let branchRef;
  try {
    ({ data: branchRef } = await github.rest.git.getRef({ owner, repo, ref: `heads/${branch}` }));
  } catch (error) {
    if (error.status !== 404) throw error;
  }

  // 이전 실행에서 PR 생성만 실패했다면 같은 버전 커밋을 재사용합니다.
  if (branchRef) {
    const { data: comparison } = await github.request('GET /repos/{owner}/{repo}/compare/{basehead}', {
      owner, repo, basehead: `${mainSha}...${branchRef.object.sha}`,
    });
    const { data: branchFile } = await github.rest.repos.getContent({ owner, repo, path: versionPath, ref: branchRef.object.sha });
    if (comparison.status !== 'ahead' || comparison.ahead_by !== 1 || comparison.files.length !== 1 ||
        comparison.files[0].filename !== versionPath || Buffer.from(branchFile.content, 'base64').toString('utf8') !== updated) {
      throw new Error(`${branch} 브랜치에 다른 변경이 있습니다. 브랜치를 확인해주세요.`);
    }
  }

  const { data: currentMain } = await github.rest.git.getRef({ owner, repo, ref: 'heads/main' });
  const { data: currentRelease } = await github.rest.repos.getLatestRelease({ owner, repo });
  if (currentMain.object.sha !== mainSha || currentRelease.tag_name !== release.tag_name) {
    throw new Error('조회 중 main 또는 이전 릴리스가 변경되었습니다. 다시 실행해주세요.');
  }
  const concurrent = await findOpenVersionPr();
  if (concurrent) return reuse(concurrent);

  await checkAssignee(assignee);

  if (dryRun) return { status: 'preview', version: changes.nextVersion, bump: changes.bump,
    branch, body: changes.body, path: versionPath, content: updated, assignee };

  if (!branchRef) {
    const { data: parent } = await github.rest.git.getCommit({ owner, repo, commit_sha: mainSha });
    const { data: blob } = await github.rest.git.createBlob({ owner, repo, content: updated, encoding: 'utf-8' });
    const { data: tree } = await github.rest.git.createTree({ owner, repo, base_tree: parent.tree.sha,
      tree: [{ path: versionPath, mode: '100644', type: 'blob', sha: blob.sha }] });
    const { data: commit } = await github.rest.git.createCommit({ owner, repo,
      message: `${changes.nextVersion} 버전 업데이트`, tree: tree.sha, parents: [mainSha] });
    await github.rest.git.createRef({ owner, repo, ref: `refs/heads/${branch}`, sha: commit.sha });
  }

  const { data: pr } = await github.rest.pulls.create({ owner, repo, base: 'main', head: branch,
    title: `${changes.nextVersion} 버전 업데이트`,
    body: `${changes.body}\n\n${requestMarker} ${JSON.stringify({ assignee, assigneeId })} -->` });
  await ensureMetadata(pr, { assignee, assigneeId }, changes.bump);
  return { status: 'created', url: pr.html_url, number: pr.number, version: changes.nextVersion, bump: changes.bump, assignee };
};
