function changes(before, after, key, value) {
  const old = new Map(before.map(item => [key(item), value(item)]));
  const next = new Map(after.map(item => [key(item), value(item)]));
  return {
    added: [...next].filter(([id]) => !old.has(id)).map(([id, value]) => ({ id, value })),
    removed: [...old].filter(([id]) => !next.has(id)).map(([id, value]) => ({ id, value })),
    changed: [...next].filter(([id, value]) => old.has(id) && old.get(id) !== value).map(([id, value]) => ({ id, before: old.get(id), after: value })),
  };
}
export function catalogDiff(previous, catalog) {
  return { from: previous?.catalogId ?? null, to: catalog.catalogId,
    sourceCompare: previous ? `https://github.com/shopl/shopl-design-guide-android/compare/${previous.sourceSha}...${catalog.sourceSha}` : null,
    api: changes(previous?.components ?? [], catalog.components, c => `${c.id}:${c.overloadId}`, c => c.signature),
    types: changes(previous?.types ?? [], catalog.types, t => t.id, t => t.declaration),
    assets: changes(previous?.assets ?? [], catalog.assets, a => a.id, a => JSON.stringify({ reference: a.reference, value: a.valueExpression })),
    note: 'Added/removed signatures are compatibility review candidates, not an automatic breaking-change classification.' };
}
