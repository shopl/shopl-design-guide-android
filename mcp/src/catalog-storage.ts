import type { Catalog } from './catalog-schema.js';

type Row = Record<string, unknown>;
const sections = ['components', 'types', 'examples', 'curatedExamples', 'assets'] as const;
const defaultFields = new Set(['receiver', 'returnType', 'annotations', 'documentation', 'constraintExpressions',
  'deprecated', 'verification', 'verifiedAgainst', 'origin', 'requiredContext', 'adaptationPoints', 'packageName',
  'kind', 'qualifier', 'valueExpression', 'type', 'reason']);
const parameterDefaults = { required: true, defaultValue: null, vararg: false, annotations: [] };
const callDefaults = { argumentCount: 0, namedArguments: [], trailingLambda: false };
const equal = (a: unknown, b: unknown) => JSON.stringify(a) === JSON.stringify(b);
function row(value: unknown): Row {
  if (!value || typeof value !== 'object' || Array.isArray(value)) throw new Error('Invalid catalog storage object');
  return value as Row;
}
function rows(value: unknown): Row[] {
  if (!Array.isArray(value)) throw new Error('Invalid catalog storage collection');
  return value.map(row);
}
function omitDefaults(value: Row, defaults: Row): Row {
  return Object.fromEntries(Object.entries(value).filter(([key, item]) => !Object.hasOwn(defaults, key) || !equal(item, defaults[key])));
}

// Storage only: preserve all API facts and restore the existing tool/feed schema before serving.
export function encodeCatalog(catalog: Catalog): Row {
  const source = structuredClone(catalog) as unknown as Row;
  const files: Row[] = [], imports: unknown[] = [];
  const fileIds = new Map<string, number>(), importIds = new Map<string, number>();
  function intern(value: unknown, table: unknown[], ids: Map<string, number>): number {
    const key = JSON.stringify(value);
    if (!ids.has(key)) { ids.set(key, table.length); table.push(value); }
    return ids.get(key)!;
  }
  function pack(records: Row[]) {
    const items = records.map(record => {
      const item = { ...record };
      const { line, ...file } = row(item.source);
      delete item.source;
      item.file = intern(file, files, fileIds); item.line = line;
      for (const key of ['imports', 'sourceImports']) {
        if (Object.hasOwn(item, key)) item[key] = intern(item[key], imports, importIds);
      }
      if (item.parameters) item.parameters = rows(item.parameters).map(p => omitDefaults(p, parameterDefaults));
      if (item.callSites) item.callSites = rows(item.callSites).map(p => omitDefaults(p, callDefaults));
      return item;
    });
    const defaults: Row = {};
    for (const key of defaultFields) {
      if (items.length < 2 || items.some(item => !Object.hasOwn(item, key))) continue;
      const counts = new Map<string, number>();
      for (const item of items) { const value = JSON.stringify(item[key]); counts.set(value, (counts.get(value) ?? 0) + 1); }
      const [value, count] = [...counts].sort((a, b) => b[1] - a[1])[0]!;
      if (count > 1) defaults[key] = JSON.parse(value);
    }
    return { defaults, items: items.map(item => omitDefaults(item, defaults)) };
  }
  for (const section of sections) source[section] = pack(rows(source[section]));
  const coverage = row(source.coverage);
  coverage.excluded = pack(rows(coverage.excluded));
  return { storageVersion: 1, files, imports, ...source };
}

export function decodeCatalog(value: unknown): unknown {
  const stored = row(value);
  if (!Object.hasOwn(stored, 'storageVersion')) return value;
  if (stored.storageVersion !== 1) throw new Error('Unsupported catalog storage version');
  const files = rows(stored.files);
  if (!Array.isArray(stored.imports) || stored.imports.some(list => !Array.isArray(list) || list.some(item => typeof item !== 'string'))) throw new Error('Invalid stored imports');
  const imports = stored.imports;
  function reference(table: unknown[], index: unknown): unknown {
    if (typeof index !== 'number' || !Number.isInteger(index) || index < 0 || index >= table.length) throw new Error('Invalid catalog storage reference');
    return table[index];
  }
  function unpack(value: unknown) {
    const collection = row(value), defaults = row(collection.defaults);
    return rows(collection.items).map(record => {
      const item = { ...defaults, ...record };
      item.source = { ...row(reference(files, item.file)), line: item.line };
      delete item.file; delete item.line;
      for (const key of ['imports', 'sourceImports']) {
        if (Object.hasOwn(item, key)) item[key] = reference(imports, item[key]);
      }
      if (item.parameters) item.parameters = rows(item.parameters).map(p => ({ ...parameterDefaults, ...p }));
      if (item.callSites) item.callSites = rows(item.callSites).map(p => ({ ...callDefaults, ...p }));
      return item;
    });
  }
  const { storageVersion, files: _files, imports: _imports, ...catalog } = stored;
  for (const section of sections) catalog[section] = unpack(stored[section]);
  catalog.coverage = { ...row(stored.coverage), excluded: unpack(row(stored.coverage).excluded) };
  return catalog;
}
