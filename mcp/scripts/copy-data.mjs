import { cpSync, mkdirSync, readdirSync, readFileSync, renameSync, unlinkSync } from 'node:fs';
import path from 'node:path';
import { mcp, catalogDir, atomicJson } from './sync-context.mjs';
const to = path.join(mcp, 'dist/data');
mkdirSync(to, { recursive: true });
const registry = JSON.parse(readFileSync(path.join(catalogDir, 'registry.json'), 'utf8'));
if (registry.catalogs.length !== 1) throw new Error('Exactly one latest catalog is required');
const entry = registry.catalogs[0];
if (!/^[a-zA-Z0-9._-]+\.json$/.test(entry.file)) throw new Error('Invalid catalog filename');
const target = path.join(to, entry.file), temporary = `${target}.${process.pid}.tmp`;
cpSync(path.join(catalogDir, entry.file), temporary);
renameSync(temporary, target);
atomicJson(path.join(to, 'registry.json'), registry);
for (const file of readdirSync(to)) {
  if (/^\d+\.\d+\.\d+-(?:[a-f0-9]+|protocol)\.json$/.test(file) && file !== entry.file) unlinkSync(path.join(to, file));
}
