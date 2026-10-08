const { execFile } = require('node:child_process');
const { promisify } = require('node:util');
const createVersionPr = require('../../.github/scripts/create-version-pr.cjs');
const execute = promisify(execFile);
const context = { repo: { owner: 'shopl', repo: 'shopl-design-guide-android' }, ref: 'refs/heads/main' };
const repoUrl = 'https://github.com/shopl/shopl-design-guide-android';

async function read(route, params = {}, paginate = false) {
  if (!route.startsWith('GET /repos/{owner}/{repo}/')) throw new Error('미리보기는 저장소 GET 조회만 허용합니다.');
  const values = { ...context.repo, ...params };
  const used = new Set();
  const path = route.slice(4).replace(/\{([^}]+)\}/g, (_, key) => {
    used.add(key);
    return encodeURIComponent(values[key]);
  });
  if (!path.startsWith('/repos/shopl/shopl-design-guide-android/')) throw new Error('테스트 저장소를 확인해주세요.');
  const args = ['api', '--method', 'GET', path];
  for (const [key, value] of Object.entries(params)) {
    if (!used.has(key)) args.push('-f', `${key}=${value}`);
  }
  if (paginate) args.push('--paginate', '--slurp');
  try {
    const { stdout } = await execute('gh', args, { maxBuffer: 8 * 1024 * 1024, timeout: 30000 });
    const data = JSON.parse(stdout);
    return paginate ? data.flat() : { data };
  } catch (error) {
    if (error.stderr?.includes('(HTTP 404)')) error.status = 404;
    throw error;
  }
}

const github = {
  request: read,
  paginate: (route, params) => read(route, params, true),
  rest: {
    pulls: { list: 'GET /repos/{owner}/{repo}/pulls' },
    repos: {
      getLatestRelease: params => read('GET /repos/{owner}/{repo}/releases/latest', params),
      getContent: params => read('GET /repos/{owner}/{repo}/contents/{path}', params),
    },
    git: { getRef: params => read('GET /repos/{owner}/{repo}/git/ref/{ref}', params) },
  },
};

const slackMarkdown = body => body.replace(/<!--[^>]*-->/g, '').trim()
  .replace(/\*\*(.+?)\*\*/g, '*$1*')
  .replace(/^## (.+)$/gm, '*$1*')
  .replace(/\[([^\]]+)\]\((https:\/\/[^\s)]+)\)/g, '<$2|$1>')
  .replace(/#(\d+)\b/g, `<${repoUrl}/pull/$1|#$1>`);

(async () => {
  const current = await createVersionPr({ github, context, dryRun: true });
  const messages = ['*SDG 버전 PR 병합 전 테스트*', 'GitHub 조회와 버전 계산만 수행합니다. 브랜치·PR·라벨은 변경하지 않습니다.', '', '*현재 main 미리보기*',
    current.status === 'existing' ? `열린 버전 PR: ${current.url}` :
      current.status === 'empty' ? '최신 릴리스 이후 변경사항이 없어 PR을 만들지 않습니다.' : slackMarkdown(current.body)];
  process.stdout.write(JSON.stringify({ message: messages.join('\n'), current }));
})().catch(error => { console.error(error.message); process.exitCode = 1; });
