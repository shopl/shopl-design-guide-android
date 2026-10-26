import { createHash } from 'node:crypto';
// Verification outcomes have their own receipts. Rechecking unchanged content preserves its ID.
export function catalogIdentity(catalog, guidance) {
  const { verification, catalogId, ...content } = catalog;
  content.provenance = Object.fromEntries(Object.entries(content.provenance).filter(([key]) => !['artifactVerified', 'artifactVerification'].includes(key)));
  content.coverage = Object.fromEntries(Object.entries(content.coverage).filter(([key]) => key !== 'exampleVerification'));
  for (const field of ['examples', 'curatedExamples']) {
    content[field] = content[field].map(({ verification, verifiedAgainst, ...example }) => example);
  }
  return `sdg-android:${catalog.version}:${createHash('sha256').update(JSON.stringify(content)).update(guidance).digest('hex').slice(0, 16)}`;
}
