import { readFileSync } from 'node:fs';
import { parseCatalog, loadCatalogs } from './catalog.js';
const catalogs = process.argv[2] ? [parseCatalog(JSON.parse(readFileSync(process.argv[2], 'utf8')))] : loadCatalogs();
for (const catalog of catalogs) {

for (const [label, records] of Object.entries({ components: catalog.components, types: catalog.types, examples: catalog.examples, assets: catalog.assets })) {
  if (!records.length) throw new Error(`Empty catalog section: ${label}`);
  if (records.some(record => record.source.sha !== catalog.sourceSha)) throw new Error(`Mixed source revisions: ${label}`);
}
if (catalog.coverage.overloads !== catalog.components.length || catalog.coverage.assets !== catalog.assets.length
    || catalog.coverage.examples !== catalog.examples.length || catalog.coverage.types !== catalog.types.length) {
  throw new Error('Catalog coverage counts do not match records');
}
console.log(`Validated ${catalog.catalogId}: ${catalog.components.length} overloads, ${catalog.assets.length} assets, ${catalog.examples.length} examples`);

}
