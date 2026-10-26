import { readFileSync } from 'node:fs';
import { AsyncLocalStorage } from 'node:async_hooks';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { z } from 'zod';
import { parseCatalog, type Catalog } from './catalog-schema.js';
import { refreshCatalog, type UpdateStatus } from './catalog-update.js';
export * from './catalog-schema.js';

const registrySchema = z.object({ schemaVersion: z.literal(1), catalogs: z.array(z.object({
  version: z.string(), sourceSha: z.string().regex(/^[a-f0-9]{40}$/), catalogId: z.string(),
  file: z.string().regex(/^[a-zA-Z0-9._-]+\.json$/),
})).length(1) });
const registryPath = process.env.SDG_CATALOG_REGISTRY ?? fileURLToPath(new URL('./data/registry.json', import.meta.url));
let cachedText: string | undefined;
let cachedCatalogs: Catalog[] = [];
export function loadCatalogs(): Catalog[] {
  const text = readFileSync(registryPath, 'utf8');
  if (text === cachedText) return cachedCatalogs;
  const registry = registrySchema.parse(JSON.parse(text));
  const catalogs = registry.catalogs.map(entry => {
    const catalog = parseCatalog(JSON.parse(readFileSync(path.join(path.dirname(registryPath), entry.file), 'utf8')));
    if (catalog.catalogId !== entry.catalogId || catalog.version !== entry.version || catalog.sourceSha !== entry.sourceSha) {
      throw new Error('Registry/catalog identity mismatch');
    }
    return catalog;
  });
  cachedText = text;
  cachedCatalogs = catalogs;
  return catalogs;
}
const context = new AsyncLocalStorage<{ catalog: Catalog; catalogs: Catalog[]; update: UpdateStatus }>();
export function currentCatalog(): Catalog {
  const value = context.getStore();
  if (!value) throw new Error('Catalog context is required');
  return value.catalog;
}
export function availableCatalogs(): Catalog[] { return context.getStore()?.catalogs ?? loadCatalogs(); }
// Capture the sole current catalog once per request, including across async handlers.
export function currentUpdate(): UpdateStatus {
  const value = context.getStore();
  if (!value) throw new Error('Catalog context is required');
  return value.update;
}
export async function inCatalog<T>(fn: () => T): Promise<Awaited<T>> {
  const snapshot = await refreshCatalog(loadCatalogs()[0]!);
  return await context.run({ catalog: snapshot.catalog, catalogs: [snapshot.catalog], update: snapshot.update }, fn);
}
