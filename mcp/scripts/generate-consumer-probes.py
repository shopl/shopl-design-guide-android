"""Generate compilation inputs from the public API and asset catalog; no runtime assertions."""
import os
import json
import re
from pathlib import Path

root = Path(__file__).resolve().parents[1]
run = Path(os.environ['SDG_SYNC_DIR'])
catalog = json.loads((run / 'catalog.json').read_text())
out = run / 'consumer-probes'
out.mkdir(parents=True, exist_ok=True)
for previous in out.glob('*.kt'):
    previous.unlink()  # This directory contains only this generator's disposable build inputs.
for index, component in enumerate(catalog['components']):
    package = component['id'].rsplit('.', 1)[0]
    parameters = component['parameters']
    opt_ins = [a for a in component['annotations'] if a.startswith('@OptIn')]
    type_text = ' '.join((p['type'] or '') for p in parameters) + ' ' + (component['receiver'] or '') + ' ' + ' '.join(opt_ins)
    imports = {'import androidx.compose.runtime.Composable', 'import ' + component['id'] + ' as CatalogTarget'}
    for statement in component['sourceImports']:
        name = statement.split(' as ')[-1] if ' as ' in statement else statement.rsplit('.', 1)[-1]
        if name == '*' or re.search(r'\b' + re.escape(name) + r'\b', type_text):
            imports.add(statement)
    declarations = ',\n    '.join(('vararg ' if p['vararg'] else '') + p['name'] + ': ' + p['type'] for p in parameters)
    arguments = ',\n        '.join(p['name'] + ' = ' + ('*' if p['vararg'] else '') + p['name'] for p in parameters)
    receiver = component['receiver'] + '.' if component['receiver'] else ''
    text = f'package {package}\n\n' + '\n'.join(sorted(imports)) + '\n\n' + '\n'.join(opt_ins) + f'\n@Composable\ninternal fun {receiver}CatalogCall{index}(\n    {declarations}\n) {{\n    CatalogTarget(\n        {arguments}\n    )\n}}\n'
    (out / f'Api{index}.kt').write_text(text)
for index, asset in enumerate(catalog['assets']):
    text = 'package com.shopl.sdg.mcp.assetprobes\n\n' + asset['import'] + f'\n\ninternal val catalogAsset{index} = ' + asset['reference'] + '\n'
    (out / f'Asset{index}.kt').write_text(text)
print(f"Generated {len(catalog['components'])} API and {len(catalog['assets'])} asset compilation inputs")
