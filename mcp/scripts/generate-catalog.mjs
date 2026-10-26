import { execFileSync } from 'node:child_process';
import { mkdirSync, readFileSync, existsSync } from 'node:fs';
import path from 'node:path';
import { mcp, repo, syncContext, atomicJson } from './sync-context.mjs';
import { consumerFingerprint } from './verification-fingerprint.mjs';
import { catalogIdentity } from './catalog-identity.mjs';
const { run, version, sourceSha: sha } = syncContext();
const snapshot = path.join(run, 'source');
if (!existsSync(snapshot)) {
  mkdirSync(snapshot);
  const archive = execFileSync('git', ['archive', sha], { cwd: repo, maxBuffer: 100 * 1024 * 1024 });
  execFileSync('tar', ['-xf', '-', '-C', snapshot], { input: archive });
}
const publishing = readFileSync(path.join(snapshot, 'build-logic/src/main/kotlin/com/shopl/sdg/build_logic/PublishingConfig.kt'), 'utf8');
if (!publishing.includes(`VERSION = "${version}"`)) throw new Error('Source version does not match the deployed version');
const output = path.join(run, 'catalog.json');
// Quote Gradle's application argument string; execFile itself does not invoke a shell.
const appArgs = [snapshot, sha, version, output, path.join(mcp, 'verification/src/main/kotlin')].map(value => `"${value.replaceAll('"', '\\"')}"`).join(' ');
execFileSync(path.join(repo, 'gradlew'), ['-p', path.join(mcp, 'extractor'), 'run', '--console=plain', `--args=${appArgs}`], { cwd: repo, stdio: 'inherit' });
const catalog = JSON.parse(readFileSync(output, 'utf8'));
const namespaceSource = readFileSync(path.join(snapshot, 'sdg-resource/build.gradle.kts'), 'utf8');
if (!namespaceSource.includes('namespace = "com.shopl.sdg_resource"')) throw new Error('Resource namespace changed; update extractor');
const androidPath = 'build-logic/src/main/kotlin/com/shopl/sdg/build_logic/KotlinAndroid.kt';
const android = readFileSync(path.join(snapshot, androidPath), 'utf8');
const minSdk = android.match(/minSdk\s*=\s*(\d+)/);
if (!minSdk) throw new Error('Cannot extract source minSdk');
catalog.setup = { minSdk: Number(minSdk[1]), source: { repository: 'https://github.com/shopl/shopl-design-guide-android', sha,
  path: androidPath, line: android.slice(0, minSdk.index).split('\n').length, kind: 'source_fact' } };
const { aliases, recipes } = await import('../dist/guidance.js');
catalog.guidance = { aliases, recipes };
catalog.artifact = { group: 'com.shoplworks', artifact: 'SDG-Android', version };
let tag = null;
try { if (execFileSync('git', ['rev-parse', `refs/tags/v${version}^{commit}`], { cwd: repo, stdio: ['ignore', 'pipe', 'ignore'], encoding: 'utf8' }).trim() === sha) tag = `v${version}`; } catch { /* Publication can finish before a tag is created. */ }
catalog.provenance = { kind: 'release_source', tag, sourceSha: sha, artifactVerified: false, extractor: 'kotlin-psi-2.1.20-v2' };
const artifactPath = path.join(run, 'artifacts.json');
const artifactReceipt = existsSync(artifactPath) ? JSON.parse(readFileSync(artifactPath, 'utf8')) : null;
if (artifactReceipt?.sourceSha === sha && artifactReceipt.version === version) {
  catalog.provenance.artifactVerified = true;
  catalog.provenance.artifactVerification = artifactReceipt;
}
const compilePath = path.join(run, 'compilation.json');
const receipt = existsSync(compilePath) ? JSON.parse(readFileSync(compilePath, 'utf8')) : null;
const sourceCompiled = receipt?.sourceSha === sha && receipt.source?.exitCode === 0;
const consumerCompiled = sourceCompiled && receipt.consumer?.exitCode === 0 && artifactReceipt
  && receipt.inputHash === consumerFingerprint(mcp, catalog, artifactPath);
if (sourceCompiled) {
  for (const example of catalog.examples) {
    example.verification = 'source_context_compiled';
    example.verifiedAgainst = { sourceSha: sha, scope: 'original_source_module', ...receipt.source };
  }
  catalog.coverage.exampleVerification = 'source_context_compiled';
}
for (const example of catalog.curatedExamples) {
  example.verification = consumerCompiled ? 'compile_verified' : 'source_only';
  if (consumerCompiled) example.verifiedAgainst = { scope: 'complete_file_against_published_artifacts',
    inputHash: receipt.inputHash, environment: receipt.environment, ...receipt.consumer };
}
catalog.verification = { sourceExamples: sourceCompiled ? 'source_context_compiled' : 'source_only',
  consumerCompilation: consumerCompiled ? 'passed' : 'not_current', receipt: consumerCompiled ? receipt : null };
catalog.catalogId = catalogIdentity(catalog, readFileSync(path.join(mcp, 'src/guidance.ts')));
atomicJson(output, catalog);
console.log(`Candidate: ${catalog.catalogId}`);
