import { execFileSync } from 'node:child_process';
import { mkdirSync, mkdtempSync, readFileSync, rmdirSync, unlinkSync } from 'node:fs';
import { createHash } from 'node:crypto';
import path from 'node:path';
import { mcp, repo, catalogDir, atomicJson } from './sync-context.mjs';
import { diagnose } from './doctor.mjs';
import { catalogDiff } from './catalog-diff.mjs';
import { parseCatalog } from '../dist/catalog-schema.js';
import { encodeCatalog } from '../dist/catalog-storage.js';

const argv = process.argv.slice(2);
const current = argv.length === 1 && argv[0] === '--current';
let request;
if (!current) {
  if (argv.length !== 4 || argv[0] !== '--version' || argv[2] !== '--source-sha') throw new Error('Usage: sync --version X.Y.Z --source-sha FULL_SHA, or --current');
  request = { version: argv[1], sourceSha: argv[3] };
}
const data = catalogDir;
const registryPath = path.join(data, 'registry.json');
const initial = JSON.parse(readFileSync(registryPath, 'utf8'));
if (initial.catalogs.length !== 1) throw new Error('Exactly one latest catalog is required');
const requests = current ? initial.catalogs : [request];
mkdirSync(path.join(mcp, '.cache/runs'), { recursive: true });
const lock = path.join(mcp, '.cache/sync.lock');
try { mkdirSync(lock); } catch { throw new Error(`Another sync is active. If it exited unexpectedly, inspect and remove ${lock}`); }
try {
  for (const release of requests) {
    const { version, sourceSha } = release;
    if (!/^\d+\.\d+\.\d+$/.test(version) || !/^[a-f0-9]{40}$/.test(sourceSha)) throw new Error('Invalid release identity');
    const registry = JSON.parse(readFileSync(registryPath, 'utf8'));
    if (registry.catalogs.length !== 1) throw new Error('Exactly one latest catalog is required');
    const previousEntry = registry.catalogs[0];
    if (version.localeCompare(previousEntry.version, 'en', { numeric: true }) < 0) throw new Error('An older release cannot replace the latest catalog');
    const existing = previousEntry.version === version ? previousEntry : undefined;
    if (existing && existing.sourceSha !== sourceSha) throw new Error('An existing release cannot be rebound to a different source SHA');
    execFileSync('git', ['cat-file', '-e', `${sourceSha}^{commit}`], { cwd: repo });
    const run = mkdtempSync(path.join(mcp, '.cache/runs', `${version}-`));
    atomicJson(path.join(run, 'release.json'), { version, sourceSha });
    const env = { ...process.env, SDG_SYNC_DIR: run };
    const node = script => execFileSync(process.execPath, [path.join(mcp, 'scripts', script)], { cwd: repo, env, stdio: 'inherit' });
    console.log(`Syncing deployed SDG ${version} at ${sourceSha}; staging: ${run}`);
    node('generate-catalog.mjs');
    execFileSync('python3', [path.join(mcp, 'scripts/verify-artifacts.py')], { cwd: repo, env, stdio: 'inherit' });
    node('verify-builds.mjs');
    node('generate-catalog.mjs');
    const catalogPath = path.join(run, 'catalog.json');
    execFileSync(process.execPath, [path.join(mcp, 'dist/validate.js'), catalogPath], { env, stdio: 'inherit' });
    const catalog = JSON.parse(readFileSync(catalogPath, 'utf8'));
    if (!catalog.provenance.artifactVerified || catalog.verification.consumerCompilation !== 'passed') throw new Error('Candidate did not pass publication gates');
    const candidateRegistry = path.join(run, 'registry.json');
    atomicJson(candidateRegistry, { schemaVersion: 1, catalogs: [{ version, sourceSha, catalogId: catalog.catalogId, file: 'catalog.json' }] });
    const protocol = await diagnose(candidateRegistry);
    atomicJson(path.join(run, 'protocol.json'), protocol);
    // All potentially failing compilation and RPC checks finish before the served registry changes.
    const filename = `${version}-${catalog.catalogId.split(':').at(-1)}.json`;
    const verificationId = createHash('sha256').update(JSON.stringify(catalog.verification)).digest('hex').slice(0, 16);
    const previous = parseCatalog(JSON.parse(readFileSync(path.join(data, previousEntry.file), 'utf8')));
    const diff = catalogDiff(previous, catalog);
    // Detailed diagnostics belong to the run/CI artifact, not the installed plugin.
    atomicJson(path.join(run, 'changes.json'), diff);
    atomicJson(path.join(data, filename), encodeCatalog(parseCatalog(catalog)));
    const entry = { version, sourceSha, catalogId: catalog.catalogId, file: filename, verificationId };
    atomicJson(registryPath, { schemaVersion: 1, catalogs: [entry] });
    // Retire the previous payload only after the new pointer is safely published.
    if (previousEntry.file !== filename) unlinkSync(path.join(data, previousEntry.file));
    console.log(`Promoted ${catalog.catalogId}; latest release only`);
  }
  execFileSync(process.execPath, [path.join(mcp, 'scripts/copy-data.mjs')], { stdio: 'inherit' });
} finally { rmdirSync(lock); }
