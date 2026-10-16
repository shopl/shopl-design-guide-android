import os,re,subprocess,json
platform=os.environ['SDG_PLATFORM'];repository=os.environ['GITHUB_REPOSITORY']
expected='shopl/shopl-design-guide-'+('android' if platform=='android' else 'ios')
assert repository==expected and os.environ['GITHUB_REF']=='refs/heads/main'
sha=os.environ['SOURCE_SHA'];base=os.environ['SOURCE_BASE'];branch=os.environ['SOURCE_BRANCH']
assert re.fullmatch('[a-f0-9]{40}',sha) and re.fullmatch('[a-f0-9]{40}',base)
assert re.fullmatch(r'(add|update|automation)/figma-[a-f0-9-]{36}',branch)
assert re.fullmatch(r'[a-f0-9-]{36}',os.environ['BUILD_ID'])
def git(*args):return subprocess.check_output(['git',*args]).decode().strip()
assert git('rev-parse','HEAD')==sha
head=json.loads(subprocess.check_output(['gh','api',f'repos/{repository}/git/ref/heads/{branch}']))
assert head['object']['sha']==sha
subprocess.run(['git','merge-base','--is-ancestor',base,'origin/main'],check=True)
subprocess.run(['git','merge-base','--is-ancestor',base,sha],check=True)
protected=['.github','.agents','AGENTS.md','CLAUDE.md','gradle','build-logic','build.gradle.kts','settings.gradle.kts','gradle.properties','Project.swift','Tuist.swift','Tuist','Package.swift','Package.resolved','.tuist-version','app/build.gradle.kts','sdg/build.gradle.kts','sdg-common','sdg-resource']
subprocess.run(['git','diff','--exit-code','origin/main','HEAD','--',*protected],check=True)
paths=git('diff','--name-only','--no-renames','-z',base,sha).split('\0')
allowed=r'(app|sdg)/src/main/java/[\w/]+\.kt' if platform=='android' else r'(Sources/ShoplDesignGuide|SampleApp/Sources)/[\w/]+\.swift'
for p in filter(None,paths):
 assert re.fullmatch(allowed,p),p
 assert not p.startswith('Sources/ShoplDesignGuide/Generated/'),p
 entry=git('ls-tree',sha,'--',p) or git('ls-tree',base,'--',p)
 assert entry.startswith('100644 blob '),p
print('Verified exact SDG source and protected paths.')
