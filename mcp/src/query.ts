import { createHash } from 'node:crypto';
import { z } from 'zod';
import { aliases } from './guidance.js';
import { currentCatalog, currentUpdate, availableCatalogs, sourceSchema, type Source } from './catalog.js';

import { updateSchema } from './catalog-update.js';

export const responseSchema = z.object({
  schemaVersion: z.literal(1), catalogId: z.string().nullable(),
  status: z.enum(['ok', 'resolved', 'declared_only', 'source_snapshot', 'unknown', 'catalog_unavailable', 'version_mismatch', 'conflict', 'not_found', 'ambiguous', 'example_unavailable']),
  update: updateSchema, data: z.record(z.string(), z.unknown()), sources: z.array(sourceSchema), warnings: z.array(z.string()),
  nextCursor: z.string().nullable().optional(), truncated: z.boolean().optional(),
});
export type Response = z.infer<typeof responseSchema>;
export const pageFields = { limit: z.number().int().min(1).max(20).default(5), cursor: z.string().max(2048).optional() };
export const catalogField = z.string().min(1).max(200).optional().describe('생략하면 최신 배포 카탈로그를 조회합니다. 지정하면 동일 스냅샷인지 확인하며 이전 ID는 context 재조회를 안내합니다.');
export function response(status: Response['status'], data: Response['data'], sources: Source[] = [], warnings: string[] = []): Response {
  const catalog = currentCatalog();
  const update = currentUpdate();
  const commonWarnings = [
    ...(update.state === 'current' ? [] : [update.detail ?? '최신 원격 SDG 정보 확인에 실패했습니다. 마지막 검증 정보를 사용하며 최신 여부는 보장하지 않습니다.']),
    ...(update.cacheWarning ? [update.cacheWarning] : []),
    ...(catalog.provenance.artifactVerified ? [] : ['배포 아티팩트와의 일치는 미검증입니다.']),
    'Kotlin 타입은 PSI 구문을 추출했습니다. 컴파일러의 타입 해석 결과가 아닙니다.',
  ];
  const unique = new Map(sources.map(s => [`${s.sha}:${s.path}:${s.line}`, s]));
  return { schemaVersion: 1, catalogId: catalog.catalogId, status, update, data, sources: [...unique.values()], warnings: [...commonWarnings, ...warnings] };
}
export function available() {
  return availableCatalogs().map(catalog => ({ catalogId: catalog.catalogId, ...catalog.artifact, sourceSha: catalog.sourceSha,
    provenance: catalog.provenance, verification: catalog.verification, usage: 'latest_release_reference' }));
}
export function checkCatalog(id: string | undefined): Response | undefined {
  if (id !== undefined && id !== currentCatalog().catalogId) return { ...response('catalog_unavailable', { requestedCatalogId: id, available: available(), nextSteps: ['sdg_resolve_context를 다시 호출하세요. 최신 카탈로그만 제공하며 이전 스냅샷은 조회할 수 없습니다.'] }), catalogId: null };
}
export function words(value: string): string[] {
  return value.toLocaleLowerCase('en').normalize('NFKC').split(/[\s,./_\-]+/u).filter(Boolean);
}
export function score(query: string, name: string, text: string): { score: number; matched: string[] } {
  const lower = text.toLocaleLowerCase('en').normalize('NFKC');
  const terms = words(query);
  const matched: string[] = [];
  let value = 0;
  for (const term of terms) {
    if (lower.includes(term)) { value += 10; matched.push(term); continue; }
    for (const [key, values] of Object.entries(currentCatalog().guidance?.aliases ?? aliases)) {
      if (values.some(v => term === v || (v.length >= 2 && term.startsWith(v))) && lower.includes(key)) {
        value += 6; matched.push(`${term} → ${key}`); break;
      }
    }
  }
  if (name.toLowerCase() === query.toLowerCase().trim()) value += 100;
  return { score: value, matched };
}
export function page<T>(items: T[], limit: number, cursor: string | undefined, key: string) {
  const fingerprint = createHash('sha256').update(`${currentCatalog().catalogId}:${key}`).digest('hex').slice(0, 20);
  let offset = 0;
  if (cursor) {
    const decoded: unknown = JSON.parse(Buffer.from(cursor, 'base64url').toString('utf8'));
    const parsed = z.object({ key: z.literal(fingerprint), offset: z.number().int().nonnegative() }).strict().parse(decoded);
    offset = parsed.offset;
    if (offset > items.length) throw new Error('Cursor is outside the result range');
  }
  const end = offset + limit;
  return { items: items.slice(offset, end), total: items.length,
    nextCursor: end < items.length ? Buffer.from(JSON.stringify({ key: fingerprint, offset: end })).toString('base64url') : null,
    truncated: end < items.length };
}
