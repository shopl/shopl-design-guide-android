// Operational diagnosis of a portable install. No source tree or node_modules is copied.
import { cpSync, mkdtempSync, readFileSync, readdirSync, rmSync } from 'node:fs';
import { createHash } from 'node:crypto';
import { tmpdir } from 'node:os';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { diagnose } from './doctor.mjs';
import { repo, atomicJson } from './sync-context.mjs';

export async function diagnosePlugin(source = path.join(repo, 'plugins/sdg-android')) {
  const temp = mkdtempSync(path.join(tmpdir(), 'sdg plugin 한글 '));
  const plugin = path.join(temp, 'plugins/sdg-android');
  try {
    cpSync(source, plugin, { recursive: true });
    const read = relative => JSON.parse(readFileSync(path.join(plugin, relative), 'utf8'));
    const release = read('release.json');
    const files = {};
    function collect(dir) {
      for (const item of readdirSync(dir, { withFileTypes: true })) {
        const full = path.join(dir, item.name), relative = path.relative(plugin, full).split(path.sep).join('/');
        if (item.isSymbolicLink()) throw new Error(`Non-portable symlink: ${relative}`);
        if (item.isDirectory()) collect(full);
        else if (relative !== 'release.json') files[relative] = createHash('sha256').update(readFileSync(full)).digest('hex');
      }
    }
    collect(plugin);
    if (JSON.stringify(Object.entries(files).sort()) !== JSON.stringify(Object.entries(release.files).sort())) throw new Error('Plugin release file hashes do not match; rebuild the bundle');
    const registry = read('server/data/registry.json');
    if (registry.catalogs.length !== 1) throw new Error('Expected latest-only registry');
    const entry = registry.catalogs[0];
    for (const [key, value] of Object.entries({ sdgVersion: entry.version, sourceSha: entry.sourceSha, catalogId: entry.catalogId, verificationId: entry.verificationId })) {
      if (release[key] !== value) throw new Error(`Release identity mismatch: ${key}`);
    }
    const sessions = [];
    for (const host of ['codex', 'claude', 'cursor']) {
      const manifest = read(`.${host}-plugin/plugin.json`);
      if (manifest.name !== 'sdg-android') throw new Error('Unexpected plugin name');
      if (host === 'codex' && manifest.version !== release.pluginVersion) throw new Error('Plugin version mismatch');
      const declaration = host === 'codex' ? read(manifest.mcpServers).mcpServers['sdg-android'] : manifest.mcpServers['sdg-android'];
      if (declaration.command !== 'node' || declaration.args.length !== 1) throw new Error('Unexpected MCP launch declaration');
      const entrypoint = path.resolve(plugin, declaration.args[0].replace('${CLAUDE_PLUGIN_ROOT}', plugin).replace('${CURSOR_PLUGIN_ROOT}', plugin));
      if (entrypoint !== path.join(plugin, 'server/index.mjs')) throw new Error('MCP entry must be in the installed plugin');
      const diagnosis = await diagnose(undefined, { entrypoint, cwd: host === 'codex' ? path.resolve(plugin, declaration.cwd) : temp });
      if (diagnosis.sessions.some(s => s.catalogs[0].catalogId !== release.catalogId)) throw new Error('Loaded catalog differs from installed release');
      sessions.push({ host, ...diagnosis });
    }
    return { status: 'passed', scope: 'manifest launch and MCP calls; host UI/automatic update not implied', release, sessions };
  } finally {
    rmSync(temp, { recursive: true, force: true });
  }
}
if (process.argv[1] && path.resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  const report = await diagnosePlugin(process.argv[2] ? path.resolve(process.argv[2]) : undefined);
  if (process.env.SDG_PLUGIN_REPORT) atomicJson(process.env.SDG_PLUGIN_REPORT, report);
  console.log(JSON.stringify({ status: report.status, catalogId: report.release.catalogId, hosts: report.sessions.map(s => ({ host: s.host, protocols: s.sessions.map(p => ({ protocol: p.protocol, calls: p.calls })) })) }, null, 2));
}
