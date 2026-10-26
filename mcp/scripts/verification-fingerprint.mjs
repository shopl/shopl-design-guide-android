import { createHash } from 'node:crypto';
import { readFileSync, readdirSync } from 'node:fs';
import path from 'node:path';

export function consumerFingerprint(mcp, catalog, artifactPath) {
  const hash = createHash('sha256');
  hash.update(JSON.stringify({ version: catalog.version, sourceSha: catalog.sourceSha, components: catalog.components, assets: catalog.assets }));
  const files = ['verification/build.gradle.kts', 'verification/settings.gradle.kts', 'verification/gradle.properties',
    'verification/src/main/AndroidManifest.xml', 'scripts/generate-consumer-probes.py', 'scripts/verify-builds.mjs', '../gradle/wrapper/gradle-wrapper.properties',
    ...readdirSync(path.join(mcp, 'verification/src/main/kotlin')).filter(n => n.endsWith('.kt')).sort().map(n => `verification/src/main/kotlin/${n}`)];
  for (const file of files) { hash.update(file); hash.update(readFileSync(path.join(mcp, file))); }
  hash.update(readFileSync(artifactPath));
  return hash.digest('hex');
}
