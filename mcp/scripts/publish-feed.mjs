// Publish one verified data snapshot atomically. No source branch, PR, tag or npm package is changed.
import { execFileSync, spawnSync } from 'node:child_process';
import { mkdtempSync, readFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import path from 'node:path';
import { mcp, repo } from './sync-context.mjs';
import { compareVersions, feedSchema, verifyFeedPayload } from '../dist/catalog-update.js';
const args = process.argv.slice(2);
if (args.length !== 0 && (args.length !== 2 || args[0] !== '--repository')) throw new Error('Usage: publish-feed.mjs [--repository LOCAL_GIT_REPOSITORY]');
const cwd = args.length ? path.resolve(args[1]) : repo;
const branch = 'sdg-mcp-data', ref = `refs/heads/${branch}`;
const feedDir = path.join(mcp, '.cache/feed');
const feed = feedSchema.parse(JSON.parse(readFileSync(path.join(feedDir, 'latest.json'), 'utf8')));
verifyFeedPayload(feed, readFileSync(path.join(feedDir, feed.file), 'utf8'));
const git = (args, options = {}) => execFileSync('git', args, { cwd, encoding: 'utf8', ...options }).trim();
const exists = spawnSync('git', ['ls-remote', '--exit-code', 'origin', ref], { cwd, encoding: 'utf8' });
if (exists.error) throw exists.error;
if (![0, 2].includes(exists.status)) throw new Error(`Could not inspect data branch: ${exists.stderr}`);
let parent;
if (exists.status === 0) {
  git(['fetch', '--no-tags', 'origin', ref]);
  parent = git(['rev-parse', 'FETCH_HEAD']);
  const previous = feedSchema.parse(JSON.parse(git(['show', `${parent}:latest.json`])));
  if (compareVersions(feed.version, previous.version) < 0) throw new Error('Refusing delayed deployment: remote already has a newer SDG');
  if (feed.version === previous.version && feed.sourceSha !== previous.sourceSha) throw new Error('A released version cannot be rebound to another source SHA');
  if (previous.publishedAt > feed.publishedAt) throw new Error('Refusing an older prepared publication');
  if (feed.sha256 === previous.sha256) { console.log('The verified payload is already published'); process.exit(0); }
}
const temp = mkdtempSync(path.join(tmpdir(), 'sdg-feed-index-'));
try {
  const env = { ...process.env, GIT_INDEX_FILE: path.join(temp, 'index') };
  const indexed = args => git(args, { env });
  indexed(parent ? ['read-tree', parent] : ['read-tree', '--empty']);
  for (const file of [feed.file, 'latest.json']) {
    const blob = git(['hash-object', '-w', path.join(feedDir, file)]);
    indexed(['update-index', '--add', '--cacheinfo', `100644,${blob},${file}`]);
  }
  const tree = indexed(['write-tree']);
  const commit = git(['-c', 'user.name=github-actions[bot]', '-c', 'user.email=41898282+github-actions[bot]@users.noreply.github.com',
    'commit-tree', tree, ...(parent ? ['-p', parent] : []), '-m', `Update verified SDG data ${feed.version}`]);
  // A normal push rejects concurrent changes. Never force a stale feed over a newer publication.
  git(['push', 'origin', `${commit}:${ref}`]);
  console.log(JSON.stringify({ branch, commit, version: feed.version, catalogId: feed.catalogId, sha256: feed.sha256 }));
} finally { rmSync(temp, { recursive: true, force: true }); }
