const { getVersionChanges } = require('./version-pr-summary.cjs');
const versionPath = 'build-logic/src/main/kotlin/com/shopl/sdg/build_logic/PublishingConfig.kt';

module.exports = async ({ github, context }) => {
  const { owner, repo } = context.repo;
  if (context.ref !== 'refs/heads/main') throw new Error('버전 PR 생성은 main에서 실행해주세요.');

  const findOpenVersionPr = async () => (await github.paginate(github.rest.pulls.list, {
    owner, repo, state: 'open', base: 'main', per_page: 100,
  })).find(pr => pr.head.ref.startsWith('version/') && pr.head.repo.full_name === `${owner}/${repo}`);
  const reuse = async pr => {
    const automationBranch = pr.head.ref.match(/^version\/automation\/(minor|patch)-\d+\.\d+\.\d+$/);
    if (automationBranch && !pr.labels.some(label => label.name === automationBranch[1])) {
      await github.rest.issues.addLabels({ owner, repo, issue_number: pr.number, labels: [automationBranch[1]] });
    }
    return { status: 'existing', url: pr.html_url, number: pr.number };
  };
  const existing = await findOpenVersionPr();
  if (existing) return reuse(existing);

  const { data: main } = await github.rest.git.getRef({ owner, repo, ref: 'heads/main' });
  const mainSha = main.object.sha;
  const { data: release } = await github.rest.repos.getLatestRelease({ owner, repo });
  const changes = await getVersionChanges({ github, context, release, headSha: mainSha });
  if (!changes.commitShas.size) return { status: 'empty' };

  const { data: file } = await github.rest.repos.getContent({ owner, repo, path: versionPath, ref: mainSha });
  if (file.type !== 'file' || file.encoding !== 'base64') throw new Error('버전 설정 파일을 확인해주세요.');
  const content = Buffer.from(file.content, 'base64').toString('utf8');
  const pattern = /\bconst val VERSION = "([^"]+)"/g;
  const versions = [...content.matchAll(pattern)];
  if (versions.length !== 1 || versions[0][1] !== release.tag_name.replace(/^v/, '')) {
    throw new Error('main의 VERSION과 이전 릴리스 버전이 다릅니다. 진행 중인 릴리스를 확인해주세요.');
  }
  const updated = content.replace(pattern, `const val VERSION = "${changes.nextVersion}"`);
  const branch = `version/automation/${changes.bump}-${changes.nextVersion}`;
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
    title: `${changes.nextVersion} 버전 업데이트`, body: changes.body });
  await github.rest.issues.addLabels({ owner, repo, issue_number: pr.number, labels: [changes.bump] });
  await github.rest.pulls.requestReviewers({ owner, repo, pull_number: pr.number, team_reviewers: ['android'] });
  return { status: 'created', url: pr.html_url, number: pr.number, version: changes.nextVersion, bump: changes.bump };
};
