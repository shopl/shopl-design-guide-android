import { currentCatalog, type Component, type Example } from './catalog.js';

/** Resolve source spelling through explicit imports, package and star imports; do not guess globally. */
export function resolveReference(name: string, imports: string[], packageName: string, known: Set<string>): string[] {
  if (known.has(name)) return [name];
  const parts = name.split('.');
  const first = parts[0]!;
  const explicit = imports.filter(i => !i.endsWith('.*')).flatMap(statement => {
    const [path, alias] = statement.replace(/^import\s+/, '').split(/\s+as\s+/);
    if (!path || (alias ?? path.split('.').at(-1)) !== first) return [];
    const target = [path, ...parts.slice(1)].join('.');
    return known.has(target) ? [target] : [];
  });
  if (explicit.length) return [...new Set(explicit)];
  const local = `${packageName}.${name}`;
  if (known.has(local)) return [local];
  return [...new Set(imports.filter(i => i.endsWith('.*')).map(i => `${i.slice(7, -1)}${name}`).filter(id => known.has(id)))];
}
export function relatedTypes(component: Component) {
  const catalog = currentCatalog();
  const typeIds = new Set(catalog.types.map(t => t.id));
  const ids = new Set(component.referencedTypes.flatMap(name => resolveReference(name, component.sourceImports, component.id.slice(0, component.id.lastIndexOf('.')), typeIds)));
  return catalog.types.filter(t => ids.has(t.id));
}
export function exampleMatches(example: Example, component: Component): boolean {
  const componentIds = new Set(currentCatalog().components.map(c => c.id));
  return example.callSites.some(call => {
    const ids = resolveReference(call.name, example.imports, example.packageName, componentIds);
    if (!ids.includes(component.id)) return false;
    if (call.namedArguments.some(name => !component.parameters.some(p => p.name === name))) return false;
    if (!component.parameters.some(p => p.vararg) && call.argumentCount > component.parameters.length) return false;
    if (call.argumentCount < component.parameters.filter(p => p.required).length) return false;
    if (!call.trailingLambda && call.argumentCount === call.namedArguments.length
        && component.parameters.some(p => p.required && !call.namedArguments.includes(p.name))) return false;
    return true;
  });
}
