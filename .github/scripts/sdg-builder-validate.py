"""Validate the exact Android SDG source before building on the Mac runner."""

import json
import os
import re
import subprocess


def require(condition, message):
    if not condition:
        raise SystemExit(message)


def git(*args):
    return subprocess.check_output(['git', *args]).decode().strip()


repository = os.environ['GITHUB_REPOSITORY']
require(repository == 'shopl/shopl-design-guide-android', 'Unexpected SDG repository.')
require(os.environ['GITHUB_REF'] == 'refs/heads/main', 'Dispatch the trusted main workflow.')
sha = os.environ['SOURCE_SHA']
base = os.environ['SOURCE_BASE']
branch = os.environ['SOURCE_BRANCH']
require(re.fullmatch(r'[a-f0-9]{40}', sha), 'Invalid source commit.')
require(re.fullmatch(r'[a-f0-9]{40}', base), 'Invalid base commit.')
require(re.fullmatch(r'(add|update|automation)/figma-[a-f0-9-]{36}', branch), 'Invalid SDG branch.')
require(re.fullmatch(r'[a-f0-9-]{36}', os.environ['BUILD_ID']), 'Invalid build ID.')
require(git('rev-parse', 'HEAD') == sha, 'Checkout does not match the requested commit.')
head = json.loads(subprocess.check_output([
    'gh', 'api', f'repos/{repository}/git/ref/heads/{branch}',
]))
require(head['object']['sha'] == sha, 'The SDG branch changed after build submission.')
subprocess.run(['git', 'merge-base', '--is-ancestor', base, 'origin/main'], check=True)
subprocess.run(['git', 'merge-base', '--is-ancestor', base, sha], check=True)

# 기준 커밋 이후에는 main Kotlin 소스만 변경할 수 있다. 삭제·이름 변경도 검사한다.
changed = subprocess.check_output([
    'git', 'diff', '--name-only', '--no-renames', '-z', base, sha,
]).decode().split('\0')
for source in filter(None, changed):
    require(re.fullmatch(r'(app|sdg)/src/main/java/[\w/]+\.kt', source),
            f'Changes outside permitted Kotlin sources: {source}')
    entry = git('ls-tree', sha, '--', source) or git('ls-tree', base, '--', source)
    require(entry.startswith('100644 blob '), f'Unexpected source file mode: {source}')

# 오래된 작업 브랜치가 최신 main의 빌드 설정·지침을 되돌리지 않도록 검사한다.
main_changes = subprocess.check_output([
    'git', 'diff', '--name-only', '--no-renames', '-z', 'origin/main', sha,
]).decode().split('\0')
for source in filter(None, main_changes):
    require(re.fullmatch(r'(app|sdg)/src/main/java/[\w/]+\.kt', source),
            f'Update the working branch to current main before building: {source}')
print('Verified exact Android SDG source and protected paths.')
