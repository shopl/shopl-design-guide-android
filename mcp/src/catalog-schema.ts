import { z } from 'zod';

export const sourceSchema = z.object({
  repository: z.string(), sha: z.string(), path: z.string(), line: z.number().int(), kind: z.enum(['source_fact', 'curated_example']),
});
const parameterSchema = z.object({
  name: z.string(), type: z.string().nullable(), required: z.boolean(), defaultValue: z.string().nullable(),
  vararg: z.boolean(), annotations: z.array(z.string()),
});
const componentSchema = z.object({
  id: z.string(), overloadId: z.string(), name: z.string(), receiver: z.string().nullable(),
  signature: z.string(), import: z.string(), sourceImports: z.array(z.string()),
  parameters: z.array(parameterSchema), referencedTypes: z.array(z.string()), returnType: z.string().nullable(), annotations: z.array(z.string()),
  documentation: z.string().nullable(), constraintExpressions: z.array(z.string()), deprecated: z.boolean(), source: sourceSchema,
});
const typeSchema = z.object({
  id: z.string(), name: z.string(), declaration: z.string(), sourceImports: z.array(z.string()).optional(), source: sourceSchema,
});
const exampleSchema = z.object({
  id: z.string(), name: z.string(), code: z.string(), imports: z.array(z.string()), calls: z.array(z.string()),
  origin: z.enum(['source_sample', 'curated']), verification: z.enum(['source_only', 'source_context_compiled', 'compile_verified', 'stale']),
  packageName: z.string(),
  callSites: z.array(z.object({ name: z.string(), argumentCount: z.number().int(), namedArguments: z.array(z.string()), trailingLambda: z.boolean() })),
  verifiedAgainst: z.record(z.string(), z.unknown()).optional(),
  requiredContext: z.array(z.string()), adaptationPoints: z.array(z.string()), source: sourceSchema,
});
export const assetKind = z.enum(['color', 'typography', 'spacing', 'radius', 'icon']);
const assetSchema = z.object({
  id: z.string(), name: z.string(), kind: assetKind, reference: z.string(), import: z.string(),
  valueExpression: z.union([z.string(), z.array(z.string())]).nullable().optional(),
  type: z.string().nullable().optional(), qualifier: z.string().optional(),
  documentation: z.string().nullable().optional(), source: sourceSchema,
});
export const catalogSchema = z.object({
  schemaVersion: z.literal(1), catalogId: z.string(), version: z.string(), sourceSha: z.string(),
  artifact: z.object({ group: z.string(), artifact: z.string(), version: z.string() }),
  provenance: z.object({ kind: z.literal('release_source'), tag: z.string().nullable(), sourceSha: z.string(), artifactVerified: z.boolean(), extractor: z.string(), artifactVerification: z.record(z.string(), z.unknown()).optional() }),
  verification: z.object({ sourceExamples: z.string(), consumerCompilation: z.string(), receipt: z.record(z.string(), z.unknown()).nullable() }),
  setup: z.object({ minSdk: z.number().int(), source: sourceSchema }),
  guidance: z.object({
    aliases: z.record(z.string(), z.array(z.string())),
    recipes: z.array(z.object({ id: z.string(), exampleId: z.string(), title: z.string(), componentNames: z.array(z.string()),
      state: z.array(z.string()), events: z.array(z.string()), decisions: z.array(z.string()) })),
  }).optional(),
  curatedExamples: z.array(exampleSchema),
  components: z.array(componentSchema), types: z.array(typeSchema), examples: z.array(exampleSchema), assets: z.array(assetSchema),
  coverage: z.object({ kotlinFiles: z.number(), overloads: z.number(), types: z.number(), examples: z.number(), assets: z.number(),
    excluded: z.array(z.object({ name: z.string(), reason: z.string(), source: sourceSchema })),
    typeResolution: z.literal('syntax_only'), exampleVerification: z.enum(['source_only', 'source_context_compiled']),
  }),
});

export type Source = z.infer<typeof sourceSchema>;
export type Component = z.infer<typeof componentSchema>;
export type Example = z.infer<typeof exampleSchema>;
export type Asset = z.infer<typeof assetSchema>;

// Catch broken extraction before serving any tool response.
function unique(values: string[], label: string) {
  if (new Set(values).size !== values.length) throw new Error(`Duplicate ${label} in catalog`);
}
export type Catalog = z.infer<typeof catalogSchema>;
export function parseCatalog(value: unknown): Catalog {
  const catalog = catalogSchema.parse(value);
  unique(catalog.components.map(c => `${c.id}:${c.overloadId}`), 'overload');
  unique(catalog.types.map(t => t.id), 'type');
  unique(catalog.assets.map(a => a.id), 'asset');
  unique([...catalog.examples, ...catalog.curatedExamples].map(e => e.id), 'example');
  return catalog;
}
