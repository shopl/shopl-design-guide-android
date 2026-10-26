import { createHash, randomUUID } from 'node:crypto';
import { mkdirSync, readdirSync, readFileSync, writeFileSync, renameSync, statSync, unlinkSync } from 'node:fs';
import { homedir } from 'node:os';
import path from 'node:path';
import { z } from 'zod';
import { parseCatalog, type Catalog } from './catalog-schema.js';

export const READER_VERSION = 1;
export const DEFAULT_FEED = 'https://raw.githubusercontent.com/shopl/shopl-design-guide-android/sdg-mcp-data/latest.json';
const MAX_PAYLOAD = 16 * 1024 * 1024;
const version = z.string().regex(/^\d+\.\d+\.\d+$/);
export const feedSchema = z.object({
  feedVersion: z.literal(1), catalogSchemaVersion: z.number().int().positive(), minimumReaderVersion: z.number().int().positive(),
  version, sourceSha: z.string().regex(/^[a-f0-9]{40}$/), catalogId: z.string().min(1),
  sha256: z.string().regex(/^[a-f0-9]{64}$/), bytes: z.number().int().positive().max(MAX_PAYLOAD),
  file: z.string().regex(/^catalogs\/[a-f0-9]{64}\.json$/), publishedAt: z.string().datetime(),
});
export type Feed = z.infer<typeof feedSchema>;
export const updateSchema = z.object({
  state: z.enum(['current', 'offline', 'client_update_required', 'remote_behind', 'disabled']),
  source: z.enum(['bundled', 'cache', 'remote']), checkedAt: z.string().nullable(), feedUrl: z.string(),
  advertisedVersion: z.string().optional(), detail: z.string().optional(), cacheWarning: z.string().optional(),
});
export type UpdateStatus = z.infer<typeof updateSchema>;
type Snapshot = { catalog: Catalog; update: UpdateStatus };
class UpdateError extends Error {
  constructor(public state: UpdateStatus['state'], message: string, public advertisedVersion?: string) { super(message); }
}
export function compareVersions(a: string, b: string): number {
  const left = a.split('.').map(Number), right = b.split('.').map(Number);
  for (let i = 0; i < 3; i++) if (left[i] !== right[i]) return left[i]! < right[i]! ? -1 : 1;
  return 0;
}
export function verifyFeedPayload(feed: Feed, payload: string): Catalog {
  if (feed.minimumReaderVersion > READER_VERSION || feed.catalogSchemaVersion !== 1) {
    throw new UpdateError('client_update_required', '새 SDG 정보 형식을 읽으려면 MCP 프로그램 업데이트가 필요합니다.', feed.version);
  }
  if (Buffer.byteLength(payload) !== feed.bytes || createHash('sha256').update(payload).digest('hex') !== feed.sha256 || feed.file !== `catalogs/${feed.sha256}.json`) throw new Error('SDG 정보 파일의 크기 또는 SHA-256이 일치하지 않습니다.');
  const catalog = parseCatalog(JSON.parse(payload));
  if (catalog.version !== feed.version || catalog.sourceSha !== feed.sourceSha || catalog.catalogId !== feed.catalogId || catalog.artifact.version !== feed.version
    || catalog.provenance.sourceSha !== feed.sourceSha || !catalog.provenance.artifactVerified || catalog.verification.consumerCompilation !== 'passed' || !catalog.verification.receipt) throw new Error('SDG 정보의 배포 식별자 또는 검증 근거가 일치하지 않습니다.');
  for (const [key, records] of Object.entries({ components: catalog.components, types: catalog.types, examples: catalog.examples, assets: catalog.assets })) {
    if (!records.length || records.some(record => record.source.sha !== feed.sourceSha)) throw new Error(`SDG 정보의 소스가 일치하지 않습니다: ${key}`);
  }
  if (catalog.coverage.overloads !== catalog.components.length || catalog.coverage.types !== catalog.types.length || catalog.coverage.examples !== catalog.examples.length || catalog.coverage.assets !== catalog.assets.length) throw new Error('SDG 정보 추출 개수가 일치하지 않습니다.');
  return catalog;
}
function feedUrl(): URL {
  const url = new URL(process.env.SDG_CATALOG_URL ?? DEFAULT_FEED);
  if (url.username || url.password || (url.protocol !== 'https:' && !(url.protocol === 'http:' && ['127.0.0.1', 'localhost', '[::1]'].includes(url.hostname)))) throw new Error('SDG 정보 주소는 HTTPS여야 합니다.');
  return url;
}
function notOlder(candidate: Catalog, current: Catalog) {
  if (compareVersions(candidate.version, current.version) < 0) throw new UpdateError('remote_behind', '원격 정보가 현재 사용 중인 SDG 버전보다 오래되어 적용하지 않았습니다.', candidate.version);
  if (candidate.version === current.version && candidate.sourceSha !== current.sourceSha) throw new Error('동일한 SDG 배포 버전의 소스 SHA를 변경할 수 없습니다.');
}
async function download(url: URL, max: number, etag?: string) {
  const res = await fetch(url, { signal: AbortSignal.timeout(4000), redirect: 'error', cache: 'no-store',
    headers: { 'Cache-Control': 'no-cache', ...(etag ? { 'If-None-Match': etag } : {}) } });
  if (res.status === 304) return { status: 304, text: '', etag };
  if (!res.ok) throw new Error(`SDG 정보 다운로드 실패: HTTP ${res.status}`);
  if (Number(res.headers.get('content-length') ?? 0) > max) { await res.body?.cancel(); throw new Error('SDG 정보 파일이 허용 크기를 초과했습니다.'); }
  if (!res.body) throw new Error('SDG 정보 응답이 비어 있습니다.');
  const reader = res.body.getReader(), chunks: Uint8Array[] = [];
  let size = 0;
  try {
    while (true) {
      const chunk = await reader.read(); if (chunk.done) break;
      size += chunk.value.length;
      if (size > max) { await reader.cancel(); throw new Error('SDG 정보 파일이 허용 크기를 초과했습니다.'); }
      chunks.push(chunk.value);
    }
  } finally { reader.releaseLock(); }
  return { status: res.status, text: Buffer.concat(chunks).toString('utf8'), etag: res.headers.get('etag') ?? undefined };
}
let active: Catalog | undefined;
let source: UpdateStatus['source'] = 'bundled';
let lastFeed: Feed | undefined;
let etag: string | undefined;
let pending: Promise<Snapshot> | undefined;
let unsaved: { dir: string; feed: Feed; payload: string } | undefined;

// Immutable cache snapshots avoid a shared mutable pointer across simultaneous agent sessions.
function cacheDir(url: URL) {
  return path.join(process.env.SDG_CATALOG_CACHE_DIR ?? path.join(homedir(), '.cache/sdg-android-mcp'), createHash('sha256').update(url.href).digest('hex').slice(0, 20));
}
function restoreCache(dir: string, current: Catalog): { catalog: Catalog; feed?: Feed; warning?: string } {
  try {
    const files = readdirSync(dir).filter(name => /^\d+\.\d+\.\d+-\d{13}-[a-f0-9]{64}\.json$/.test(name))
      .sort((a, b) => compareVersions(b.split('-')[0]!, a.split('-')[0]!) || b.localeCompare(a));
    let warning: string | undefined;
    for (const file of files) {
      try {
        const full = path.join(dir, file);
        if (statSync(full).size > MAX_PAYLOAD * 2 + 65536) throw new Error('Cache too large');
        const saved = JSON.parse(readFileSync(full, 'utf8'));
        const feed = feedSchema.parse(saved.feed);
        const catalog = verifyFeedPayload(feed, saved.payload);
        if (compareVersions(catalog.version, current.version) < 0) continue;
        notOlder(catalog, current);
        return { catalog, feed, warning };
      } catch { warning = '손상되거나 호환되지 않는 로컬 저장본을 건너뛰었습니다.'; }
    }
    return { catalog: current, warning };
  } catch (error) {
    return { catalog: current, warning: (error as NodeJS.ErrnoException).code === 'ENOENT' ? undefined : '로컬 SDG 저장본을 읽지 못했습니다.' };
  }
}
function saveCache(dir: string, feed: Feed, payload: string): string | undefined {
  let temp: string | undefined;
  try {
    mkdirSync(dir, { recursive: true });
    const target = path.join(dir, `${feed.version}-${Date.parse(feed.publishedAt)}-${feed.sha256}.json`);
    temp = `${target}.${randomUUID()}.tmp`;
    writeFileSync(temp, JSON.stringify({ feed, payload }));
    renameSync(temp, target);
  } catch {
    if (temp) { try { unlinkSync(temp); } catch {} }
    return '최신 정보는 이번 세션에서 사용하지만 로컬 저장에 실패했습니다. 오프라인 재시작 시 이전 정보를 사용할 수 있습니다.';
  }
  return undefined;
}
async function refresh(bundled: Catalog): Promise<Snapshot> {
  if (!active || compareVersions(bundled.version, active.version) > 0) { active = bundled; source = 'bundled'; }
  const configured = process.env.SDG_CATALOG_URL ?? DEFAULT_FEED;
  if (process.env.SDG_CATALOG_UPDATES === 'off' || process.env.SDG_CATALOG_REGISTRY) {
    return { catalog: bundled, update: { state: 'disabled', source: 'bundled', checkedAt: null, feedUrl: configured, detail: '검증 또는 명시적 고정 설정으로 자동 갱신이 꺼져 있습니다. 최신 원격 배포 여부는 미확인입니다.' } };
  }
  let cacheWarning: string | undefined;
  try {
    const url = feedUrl(), dir = cacheDir(url);
    const restored = restoreCache(dir, active);
    cacheWarning = restored.warning;
    if (unsaved) {
      const warning = saveCache(unsaved.dir, unsaved.feed, unsaved.payload);
      if (!warning) unsaved = undefined;
      else cacheWarning = warning;
    }
    if (restored.feed && (!lastFeed || compareVersions(restored.catalog.version, active.version) > 0 || restored.feed.publishedAt > lastFeed.publishedAt)) {
      active = restored.catalog; lastFeed = restored.feed; source = 'cache'; etag = undefined;
    }
    const checkUrl = new URL(url);
    checkUrl.searchParams.set('check', String(Date.now()));
    const response = await download(checkUrl, 65536, etag);
    if (response.status !== 304) {
      const value = JSON.parse(response.text);
      if (value && typeof value.feedVersion === 'number' && value.feedVersion !== 1) throw new UpdateError('client_update_required', '새 게시 형식을 읽으려면 MCP 프로그램 업데이트가 필요합니다.', typeof value.version === 'string' ? value.version : undefined);
      const feed = feedSchema.parse(value);
      if (feed.minimumReaderVersion > READER_VERSION || feed.catalogSchemaVersion !== 1) throw new UpdateError('client_update_required', '새 SDG 정보 형식을 읽으려면 MCP 프로그램 업데이트가 필요합니다.', feed.version);
      if (compareVersions(feed.version, active.version) < 0) throw new UpdateError('remote_behind', '원격 정보가 현재 사용 중인 SDG 버전보다 오래되어 적용하지 않았습니다.', feed.version);
      if (feed.version === active.version && feed.sourceSha !== active.sourceSha) throw new Error('동일한 SDG 배포 버전의 소스 SHA가 변경되었습니다.');
      if (lastFeed && feed.version === lastFeed.version && feed.publishedAt < lastFeed.publishedAt) throw new UpdateError('remote_behind', '원격 게시 정보가 이미 확인한 게시 시점보다 오래되어 적용하지 않았습니다.', feed.version);
      if (!lastFeed || JSON.stringify(feed) !== JSON.stringify(lastFeed)) {
        const response = await download(new URL(feed.file, url), MAX_PAYLOAD);
        const candidate = verifyFeedPayload(feed, response.text);
        notOlder(candidate, active);
        const warning = saveCache(dir, feed, response.text);
        unsaved = warning ? { dir, feed, payload: response.text } : undefined;
        cacheWarning = warning ?? cacheWarning;
        active = candidate;
      }
      lastFeed = feed; etag = response.etag; source = 'remote';
    } else if (!lastFeed) throw new Error('확인할 저장본 없이 HTTP 304를 받았습니다.');
    return { catalog: active, update: { state: 'current', source, checkedAt: new Date().toISOString(), feedUrl: url.href, advertisedVersion: lastFeed!.version, cacheWarning } };
  } catch (error) {
    return { catalog: active!, update: { state: error instanceof UpdateError ? error.state : 'offline', source,
      checkedAt: new Date().toISOString(), feedUrl: configured, advertisedVersion: error instanceof UpdateError ? error.advertisedVersion : undefined,
      detail: error instanceof Error ? error.message : '최신 SDG 정보를 확인하지 못했습니다.', cacheWarning } };
  }
}
export async function refreshCatalog(bundled: Catalog): Promise<Snapshot> {
  if (!pending) pending = refresh(bundled).finally(() => { pending = undefined; });
  return pending;
}
