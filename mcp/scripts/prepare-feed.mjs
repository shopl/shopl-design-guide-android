// Public feed contains JSON data only. It never carries executable updates.
import { createHash } from 'node:crypto';
import { mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import path from 'node:path';
import { mcp, catalogDir, atomicJson } from './sync-context.mjs';
import { parseCatalog } from '../dist/catalog-schema.js';
import { feedSchema, verifyFeedPayload, READER_VERSION } from '../dist/catalog-update.js';
const registry = JSON.parse(readFileSync(path.join(catalogDir, 'registry.json'), 'utf8'));
if (registry.catalogs.length !== 1) throw new Error('Only the latest catalog can be published');
const entry = registry.catalogs[0];
if (!/^[a-zA-Z0-9._-]+\.json$/.test(entry.file)) throw new Error('Invalid catalog filename');
// Keep the public schema unchanged so already-installed readers can fetch new releases.
const catalog = parseCatalog(JSON.parse(readFileSync(path.join(catalogDir, entry.file), 'utf8')));
const payload = JSON.stringify(catalog) + '\n';
const sha256 = createHash('sha256').update(payload).digest('hex');
const feed = feedSchema.parse({ feedVersion: 1, catalogSchemaVersion: 1, minimumReaderVersion: READER_VERSION,
  version: entry.version, sourceSha: entry.sourceSha, catalogId: entry.catalogId,
  sha256, bytes: Buffer.byteLength(payload), file: `catalogs/${sha256}.json`, publishedAt: new Date().toISOString() });
verifyFeedPayload(feed, payload);
const out = path.join(mcp, '.cache/feed');
mkdirSync(path.join(out, 'catalogs'), { recursive: true });
writeFileSync(path.join(out, feed.file), payload);
atomicJson(path.join(out, 'latest.json'), feed);
console.log(JSON.stringify({ out, version: feed.version, sha256, catalogId: feed.catalogId }));
