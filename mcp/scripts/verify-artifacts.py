"""Compare the pinned source with published sources and resource symbols."""
import os
import hashlib
import json
import urllib.request
import urllib.error
import time
import zipfile
from pathlib import Path

root = Path(__file__).resolve().parents[1]
run = Path(os.environ['SDG_SYNC_DIR'])
catalog = json.loads((run / 'catalog.json').read_text())
version = catalog['version']
cache = run / 'artifacts'
cache.mkdir(parents=True, exist_ok=True)
snapshot = run / 'source'
def download(url):
    # PUBLISHED can precede public repository propagation. Retry only transient failures.
    for attempt in range(6):
        try:
            with urllib.request.urlopen(url, timeout=30) as response:
                return response.read()
        except (urllib.error.URLError, TimeoutError) as error:
            if isinstance(error, urllib.error.HTTPError) and error.code not in [404, 429, 500, 502, 503, 504]:
                raise
            if attempt == 5:
                raise
            time.sleep(5 * (attempt + 1))

downloads = []
source_counts = {}
for artifact, module in [('SDG-Android', 'sdg'), ('SDG-Android-Common', 'sdg-common'), ('SDG-Android-Resource', 'sdg-resource')]:
    for suffix in ['-sources.jar', '.aar', '.pom']:
        name = f'{artifact}-{version}{suffix}'
        path = cache / name
        url = f'https://repo.maven.apache.org/maven2/com/shoplworks/{artifact}/{version}/{name}'
        if not path.exists():
            path.write_bytes(download(url))
        downloads.append({'artifact': artifact, 'file': name, 'url': url, 'sha256': hashlib.sha256(path.read_bytes()).hexdigest()})
    if module == 'sdg-resource':
        continue
    with zipfile.ZipFile(cache / f'{artifact}-{version}-sources.jar') as jar:
        names = {name for name in jar.namelist() if name.endswith('.kt')}
        local = snapshot / module / 'src/main/java'
        local_names = {str(path.relative_to(local)) for path in local.rglob('*.kt')}
        if names != local_names:
            raise RuntimeError(f'{artifact}: source file inventory differs: {names ^ local_names}')
        for name in sorted(names):
            if jar.read(name) != (local / name).read_bytes():
                raise RuntimeError(f'{artifact}: source bytes differ: {name}')
        source_counts[artifact] = len(names)
with zipfile.ZipFile(cache / f'SDG-Android-Resource-{version}.aar') as aar:
    symbols = {line.split()[2] for line in aar.read('R.txt').decode().splitlines() if line.startswith('int drawable ')}
    names = {asset['name'] for asset in catalog['assets'] if asset['kind'] == 'icon'}
    if not names <= symbols:
        raise RuntimeError(f'Missing published drawable symbols: {names - symbols}')
receipt = {'sourceSha': catalog['sourceSha'], 'version': version, 'scope': 'published_source_bytes_and_drawable_symbols',
           'sourceCounts': source_counts, 'drawableSymbols': len(names), 'artifacts': downloads}
target = run / 'artifacts.json'
data = root.parent / 'plugins/sdg-android/server/data'
registry = json.loads((data / 'registry.json').read_text())
for entry in registry['catalogs']:
    if entry['version'] != version:
        continue
    previous_catalog = json.loads((data / entry['file']).read_text())
    previous = previous_catalog['provenance'].get('artifactVerification')
    if previous and previous['artifacts'] != receipt['artifacts']:
        raise RuntimeError('Published artifact bytes changed for an existing version')
target.write_text(json.dumps(receipt, ensure_ascii=False, indent=2) + '\n')
print('Published source bytes match:', source_counts, '; drawable symbols:', len(names))
