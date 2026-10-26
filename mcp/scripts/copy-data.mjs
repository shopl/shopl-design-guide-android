import { cpSync, mkdirSync, readdirSync, readFileSync, unlinkSync } from 'node:fs';
import path from 'node:path';
import { mcp, atomicJson } from './sync-context.mjs';
const from = path.join(mcp, 'data');
const to = path.join(mcp, 'dist/data');
mkdirSync(to, { recursive: true });
const registry = JSON.parse(readFileSync(path.join(from, 'registry.json'), 'utf8'));
if (registry.catalogs.length !== 1) throw new Error('Exactly one latest catalog is required');
const entry = registry.catalogs[0];
for (const file of [entry.file, `${entry.version}-protocol.json`, 'changes']) {
  if (file.endsWith('.json')) atomicJson(path.join(to, file), JSON.parse(readFileSync(path.join(from, file), 'utf8')));
  else cpSync(path.join(from, file), path.join(to, file), { recursive: true });
}
// A running server notices this final atomic switch on its next tool request.
atomicJson(path.join(to, 'registry.json'), registry);
// Remove retired catalog payloads from previous builds after the atomic switch.
for (const file of readdirSync(to)) {
  if (/^\d+\.\d+\.\d+-(?:[a-f0-9]+|protocol)\.json$/.test(file) && file !== entry.file && file !== `${entry.version}-protocol.json`) unlinkSync(path.join(to, file));
}
