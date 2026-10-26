import { execFileSync, spawnSync } from 'node:child_process';
import { readFileSync, openSync, closeSync } from 'node:fs';
import { createHash } from 'node:crypto';
import path from 'node:path';
import { mcp, repo, syncContext, atomicJson } from './sync-context.mjs';
import { consumerFingerprint } from './verification-fingerprint.mjs';
const { run, version } = syncContext();
const catalog = JSON.parse(readFileSync(path.join(run, 'catalog.json'), 'utf8'));
function compile(wrapper, project, task, logName, properties = []) {
  const log = path.join(run, logName);
  const fd = openSync(log, 'w');
  const args = ['-p', project, task, '--console=plain', '--max-workers=4', ...properties];
  let result;
  try { result = spawnSync(wrapper, args, { cwd: repo, stdio: ['ignore', fd, fd] }); }
  finally { closeSync(fd); }
  if (result.error) throw result.error;
  if (result.status !== 0) throw new Error(`Compile failed (${result.status}); see ${log}`);
  console.log(`Passed ${task} (${version})`);
  return { task, exitCode: result.status, log: logName, logSha256: createHash('sha256').update(readFileSync(log)).digest('hex') };
}
const source = compile(path.join(run, 'source/gradlew'), path.join(run, 'source'), ':app:compileDebugKotlin', 'source-compile.log');
execFileSync('python3', [path.join(mcp, 'scripts/generate-consumer-probes.py')], { stdio: 'inherit' });
const consumer = compile(path.join(repo, 'gradlew'), path.join(mcp, 'verification'), 'compileDebugKotlin', 'consumer-compile.log',
  [`-PsdgVersion=${version}`, `-PsdgProbeDir=${path.join(run, 'consumer-probes')}`, `-PsdgBuildDir=${path.join(run, 'consumer-build')}`]);
const receipt = { sourceSha: catalog.sourceSha, source, consumer,
  inputHash: consumerFingerprint(mcp, catalog, path.join(run, 'artifacts.json')), verifiedAt: new Date().toISOString(),
  apiOverloads: catalog.components.length, assets: catalog.assets.length, sourceExamples: catalog.examples.length, curatedExamples: catalog.curatedExamples.length,
  environment: { kotlin: '2.1.20', androidGradlePlugin: '8.10.0', compileSdk: 36, minSdk: 28, sdg: version, composeBom: '2025.05.01' } };
atomicJson(path.join(run, 'compilation.json'), receipt);
