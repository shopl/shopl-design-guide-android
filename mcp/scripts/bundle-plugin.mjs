// Build the self-contained consumer artifact from the already verified current catalog.
import { build } from 'esbuild';
import { cpSync, mkdirSync, readFileSync, readdirSync, renameSync, rmSync, writeFileSync } from 'node:fs';
import { createHash } from 'node:crypto';
import path from 'node:path';
import { mcp, repo } from './sync-context.mjs';
import { READER_VERSION, DEFAULT_FEED } from '../dist/catalog-update.js';
import { parseCatalog } from '../dist/catalog.js';

const plugin = path.join(repo, 'plugins/sdg-android');
const pkg = JSON.parse(readFileSync(path.join(mcp, 'package.json'), 'utf8'));
const registry = JSON.parse(readFileSync(path.join(mcp, 'data/registry.json'), 'utf8'));
if (registry.catalogs.length !== 1) throw new Error('Exactly one released catalog is required');
const entry = registry.catalogs[0];
if (!/^[a-zA-Z0-9._-]+\.json$/.test(entry.file)) throw new Error('Unsafe catalog filename');
const catalog = parseCatalog(JSON.parse(readFileSync(path.join(mcp, 'data', entry.file), 'utf8')));
if (catalog.catalogId !== entry.catalogId || catalog.sourceSha !== entry.sourceSha || catalog.version !== entry.version || !catalog.provenance.artifactVerified || !catalog.verification.receipt) {
  throw new Error('Catalog identity or published-artifact verification is missing');
}
const stage = path.join(plugin, `.server-${process.pid}`);
mkdirSync(path.join(stage, 'data'), { recursive: true });
try {
  await build({
    entryPoints: [path.join(mcp, 'src/index.ts')], outfile: path.join(stage, 'index.mjs'),
    bundle: true, platform: 'node', format: 'esm', target: 'node22', minify: true, legalComments: 'eof',
    define: { __SDG_MCP_VERSION__: JSON.stringify(pkg.version) },
    banner: { js: "#!/usr/bin/env node\nimport { createRequire as sdgCreateRequire } from 'node:module';\nconst require = sdgCreateRequire(import.meta.url);" },
  });
  for (const file of ['registry.json', entry.file]) cpSync(path.join(mcp, 'data', file), path.join(stage, 'data', file));
  const manifest = path.join(plugin, '.codex-plugin/plugin.json');
  const codex = JSON.parse(readFileSync(manifest, 'utf8'));
  codex.version = pkg.version;
  writeFileSync(manifest, JSON.stringify(codex, null, 2) + '\n');
  // Only generated server output is replaced; skills and host manifests remain source files.
  rmSync(path.join(plugin, 'server'), { recursive: true, force: true });
  renameSync(stage, path.join(plugin, 'server'));
  const files = {};
  function collect(dir) {
    for (const item of readdirSync(dir, { withFileTypes: true }).sort((a, b) => a.name.localeCompare(b.name, 'en'))) {
      const full = path.join(dir, item.name), relative = path.relative(plugin, full).split(path.sep).join('/');
      if (item.isDirectory()) collect(full);
      else if (relative !== 'release.json') files[relative] = createHash('sha256').update(readFileSync(full)).digest('hex');
    }
  }
  collect(plugin);
  const release = { schemaVersion: 1, pluginVersion: pkg.version, node: pkg.engines.node,
    catalogUpdate: { mode: 'automatic', readerVersion: READER_VERSION, feedUrl: DEFAULT_FEED },
    sdgVersion: entry.version, sourceSha: entry.sourceSha, catalogId: entry.catalogId, verificationId: entry.verificationId, files };
  writeFileSync(path.join(plugin, 'release.json'), JSON.stringify(release, null, 2) + '\n');
  console.log(JSON.stringify({ plugin, ...entry, pluginVersion: pkg.version }));
} finally {
  rmSync(stage, { recursive: true, force: true });
}
