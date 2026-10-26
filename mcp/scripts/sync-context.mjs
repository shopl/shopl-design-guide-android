import { readFileSync, writeFileSync, renameSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import path from 'node:path';
export const mcp = fileURLToPath(new URL('../', import.meta.url));
export const repo = path.resolve(mcp, '..');
export const catalogDir = path.join(repo, 'plugins/sdg-android/server/data');
export function syncContext() {
  if (!process.env.SDG_SYNC_DIR) throw new Error('Run npm run sync -- --version VERSION --source-sha SHA');
  const run = path.resolve(process.env.SDG_SYNC_DIR);
  return { run, ...JSON.parse(readFileSync(path.join(run, 'release.json'), 'utf8')) };
}
export function atomicJson(file, data) {
  const temp = `${file}.${process.pid}.tmp`;
  writeFileSync(temp, JSON.stringify(data, null, 2) + '\n');
  renameSync(temp, file);
}
