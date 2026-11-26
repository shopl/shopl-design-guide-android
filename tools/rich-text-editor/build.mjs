import { build } from 'esbuild'
import { mkdir, readdir, readFile, writeFile } from 'node:fs/promises'
import { fileURLToPath } from 'node:url'

const project = new URL('./', import.meta.url)
const output = new URL(
  process.argv[2] ?? '../../sdg/src/main/assets/shopl/sdg/rich-text/',
  project,
)

const bundle = await build({
  absWorkingDir: fileURLToPath(project),
  entryPoints: ['editor.js'],
  bundle: true,
  format: 'iife',
  target: 'chrome75',
  minify: true,
  metafile: true,
  write: false,
})

const packages = new Set()
for (const input of Object.keys(bundle.metafile.inputs)) {
  const name = input.match(/^node_modules\/((?:@[^/]+\/)?[^/]+)\//)?.[1]
  if (name) packages.add(name)
}
const notices = await Promise.all([...packages].sort().map(async (name) => {
  const directory = new URL(`node_modules/${name}/`, project)
  const license = (await readdir(directory)).find((file) => /^license(?:\.|$)/i.test(file))
  return `${name}\n\n${await readFile(new URL(license, directory), 'utf8')}`
}))

await mkdir(output, { recursive: true })
await writeFile(new URL('editor.js', output), bundle.outputFiles[0].contents)
await writeFile(new URL('licenses.txt', output), notices.join('\n---\n\n'))
