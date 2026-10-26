import { spawn } from 'node:child_process';
import { fileURLToPath } from 'node:url';
import path from 'node:path';
import { mcp, atomicJson } from './sync-context.mjs';

export async function diagnose(registryPath, options = {}) {
  const sessions = [];
  for (const protocol of ['2025-11-25', '2026-07-28']) {
    const env = { ...process.env };
    delete env.SDG_CATALOG_REGISTRY;
    // Publication gates diagnose the supplied snapshot, never a changing remote feed.
    env.SDG_CATALOG_UPDATES = 'off';
    if (registryPath) env.SDG_CATALOG_REGISTRY = registryPath;
    const child = spawn(process.execPath, [options.entrypoint ?? path.join(mcp, 'dist/index.js')], {
      cwd: options.cwd, env, stdio: ['pipe', 'pipe', 'pipe'],
    });
    let buffer = '', sequence = 0, stderr = '';
    const pending = new Map();
    const fail = error => { for (const waiter of pending.values()) waiter.reject(error); pending.clear(); };
    child.on('error', fail);
    child.on('exit', code => fail(new Error(`Server exited (${code}): ${stderr.slice(-1000)}`)));
    child.stderr.on('data', chunk => { stderr += chunk; });
    child.stdout.on('data', chunk => {
      buffer += chunk;
      let end;
      while ((end = buffer.indexOf('\n')) >= 0) {
        const line = buffer.slice(0, end); buffer = buffer.slice(end + 1);
        try {
          const message = JSON.parse(line);
          const waiter = pending.get(message.id);
          if (waiter) { pending.delete(message.id); message.error ? waiter.reject(new Error(JSON.stringify(message.error))) : waiter.resolve(message.result); }
        } catch (error) { fail(error); }
      }
    });
    const modern = protocol === '2026-07-28';
    const meta = { 'io.modelcontextprotocol/protocolVersion': protocol,
      'io.modelcontextprotocol/clientCapabilities': {}, 'io.modelcontextprotocol/clientInfo': { name: 'sdg-doctor', version: '1.0' } };
    function rpc(method, params = {}) {
      return new Promise((resolve, reject) => {
        const id = ++sequence;
        const timer = setTimeout(() => { pending.delete(id); reject(new Error(`Timed out: ${method}`)); }, 15000);
        pending.set(id, { resolve: value => { clearTimeout(timer); resolve(value); }, reject: error => { clearTimeout(timer); reject(error); } });
        child.stdin.write(JSON.stringify({ jsonrpc: '2.0', id, method, params: modern ? { ...params, _meta: meta } : params }) + '\n');
      });
    }
    let calls = 0;
    async function call(name, args) {
      const result = await rpc('tools/call', { name, arguments: args }); calls++;
      if (result.isError || !result.structuredContent) throw new Error(`Tool failed: ${name}`);
      return result.structuredContent;
    }
    try {
      await rpc(modern ? 'server/discover' : 'initialize', modern ? {} : { protocolVersion: protocol, capabilities: {}, clientInfo: { name: 'sdg-doctor', version: '1.0' } });
      if (!modern) child.stdin.write(JSON.stringify({ jsonrpc: '2.0', method: 'notifications/initialized' }) + '\n');
      const inventory = await rpc('tools/list');
      const context = await call('sdg_resolve_context', {});
      if (context.status !== 'ok' || context.data.policy !== 'latest_only' || context.data.available.length !== 1 || context.catalogId !== context.data.available[0].catalogId) throw new Error('Expected exactly one default latest catalog');
      const mismatch = await call('sdg_resolve_context', { module: ':consumer', variant: 'debug', dependencyEvidence: [{
        group: 'com.shoplworks', artifact: 'SDG-Android', version: '0.0.0', kind: 'declared', source: 'connection diagnostic: outdated consumer',
      }] });
      if (mismatch.status !== 'version_mismatch' || mismatch.catalogId !== context.catalogId || mismatch.data.compatibility !== 'mismatch') throw new Error('Outdated consumer must still receive latest reference with mismatch warning');
      const stale = await call('sdg_get_recipe', { catalogId: 'retired-catalog' });
      if (stale.status !== 'catalog_unavailable') throw new Error('Stale snapshot must require refresh');
      if (context.data.reuse?.projectKit?.indexedByMcp !== false || !context.data.reuse?.catalogCategories?.includes('template')) throw new Error('Component/template and local kit guidance is missing');
      const categoryChecks = [];
      for (const [category, query] of [['component', 'SDGBoxButton'], ['template', 'SDGInputCenterPopup']]) {
        const found = await call('sdg_search_components', { query, category });
        const component = found.data.components?.[0];
        if (found.status !== 'ok' || !component?.componentId.includes(`.${category}.`)) throw new Error(`No ${category} candidate found`);
        const detail = await call('sdg_get_component', { componentId: component.componentId, overloadId: component.overloadId });
        if (detail.status !== 'ok' || !detail.data.component.import.includes(`.${category}.`)) throw new Error(`No exact ${category} API found`);
        categoryChecks.push({ category, componentId: component.componentId, status: 'passed' });
      }
      const catalogs = [];
      for (const entry of context.data.available) {
        const resolved = await call('sdg_resolve_context', { module: ':consumer', variant: 'debug', dependencyEvidence: [{ group: entry.group,
          artifact: entry.artifact, version: entry.version, kind: 'declared', source: 'connection diagnostic' }] });
        if (resolved.status !== 'declared_only' || resolved.catalogId !== entry.catalogId) throw new Error('Wrong version selected');
        const catalogId = entry.catalogId;
        const found = await call('sdg_search_components', { query: 'SDGBoxButton' });
        const component = found.data.components[0];
        if (!component) throw new Error('No usable component');
        const detail = await call('sdg_get_component', { componentId: component.componentId, overloadId: component.overloadId });
        if (detail.status !== 'ok') throw new Error('Component detail unavailable');
        const recipes = await call('sdg_get_recipe', {});
        for (const r of recipes.data.recipes) {
          const recipe = await call('sdg_get_recipe', { recipeId: r.recipeId });
          const example = await call('sdg_get_example', { exampleId: recipe.data.recipe.exampleId });
          if (example.status !== 'ok' || example.data.example.verification !== 'compile_verified') throw new Error('Recipe example has no current compilation evidence');
        }
        const assets = await call('sdg_search_assets', { kind: 'icon' });
        if (assets.status !== 'ok') throw new Error('Assets unavailable');
        catalogs.push({ version: entry.version, catalogId, status: 'passed' });
      }
      sessions.push({ protocol, categoryChecks, policy: 'latest_only', checks: ['default_latest', 'outdated_consumer_warning', 'stale_snapshot_rejected', 'all_tools_without_catalog_id'], tools: inventory.tools.map(tool => tool.name), calls, catalogs });
    } finally {
      fail(new Error('Diagnostic finished'));
      child.stdin.end(); child.kill();
    }
  }
  return { verifiedAt: new Date().toISOString(), status: 'passed', sessions };
}
if (process.argv[1] && path.resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  const result = await diagnose(process.env.SDG_CATALOG_REGISTRY);
  if (process.env.SDG_DOCTOR_REPORT) atomicJson(process.env.SDG_DOCTOR_REPORT, result);
  console.log(JSON.stringify(result, null, 2));
}
