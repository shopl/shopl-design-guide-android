import { z } from 'zod';
import { McpServer } from '@modelcontextprotocol/server';
import { currentCatalog, inCatalog, assetKind, type Component } from './catalog.js';
import { relatedTypes, exampleMatches } from './references.js';
import { recipes } from './guidance.js';
import { available, catalogField, checkCatalog, page, pageFields, response, responseSchema, score, type Response } from './query.js';

const evidenceSchema = z.object({
  group: z.string().min(1), artifact: z.string().min(1), version: z.string().min(1),
  kind: z.enum(['resolved', 'declared', 'source']), source: z.string().min(1).max(4000),
  sourceSha: z.string().regex(/^[a-f0-9]{40}$/).optional(), dirty: z.boolean().optional(),
}).strict().refine(e => e.kind !== 'source' || !!e.sourceSha, 'source 근거에는 sourceSha가 필요합니다.');
const annotations = { readOnlyHint: true, destructiveHint: false, idempotentHint: true, openWorldHint: true };
function result(value: Response) {
  return { content: [{ type: 'text' as const, text: JSON.stringify(value) }], structuredContent: value };
}
function summary(c: Component) {
  return { componentId: c.id, overloadId: c.overloadId, name: c.name, receiver: c.receiver,
    deprecated: c.deprecated, documentation: c.documentation, source: c.source };
}
function exampleSummaries(components: Component[]) {
  const catalog = currentCatalog();
  return [...catalog.curatedExamples, ...catalog.examples].flatMap(e => {
    const matches = components.filter(c => exampleMatches(e, c));
    return matches.length ? [{ exampleId: e.id, name: e.name, verification: e.verification, source: e.source,
      matching: 'imports_and_argument_shape', overloadIds: matches.map(c => c.overloadId) }] : [];
  });
}

export function registerTools(server: McpServer) {
  server.registerTool('sdg_resolve_context', {
    description: '최신 배포 SDG 카탈로그와 설정 안내를 반환합니다. 의존성 근거를 전달하면 소비 앱과 최신 버전의 차이를 확인합니다. 버전을 선택하지 않습니다. 파일이나 Gradle을 직접 읽거나 실행하지 않습니다.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({
      projectKey: z.string().max(300).optional(), module: z.string().max(300).optional(), variant: z.string().max(100).optional(),
      dependencyEvidence: z.array(evidenceSchema).max(20).default([]),
      environment: z.object({ minSdk: z.number().int().positive().optional(), kotlin: z.string().optional(), compose: z.string().optional() }).strict().optional(),
    }).strict(),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const scope = { projectKey: args.projectKey, module: args.module, variant: args.variant };
    const evidence = args.dependencyEvidence.filter(e => e.group === catalog.artifact.group && e.artifact === catalog.artifact.artifact);
    const base = { policy: 'latest_only', reuse: { catalogCategories: ['component', 'template'], projectKit: { source: 'consumer_repository', path: 'ui/src/main/java/me/planetory/io/ui/sdg_kit', indexedByMcp: false }, preference: '도메인 조합은 기존 kit, 공통 패턴은 template, 기본 요소는 component를 우선 재사용합니다.' }, scope, available: available(), evidence, evidenceTrust: 'caller_supplied',
      setup: { dependency: `${catalog.artifact.group}:${catalog.artifact.artifact}:${catalog.version}`, repository: 'mavenCentral()',
        steps: ['대상 모듈과 convention plugin의 기존 SDG 의존성을 확인하세요.', 'SDG component·template는 MCP에서, 본 레포 kit의 component·template와 호출부는 ui/sdg_kit에서 검색하여 우선 재사용하세요.', '소비 앱 버전이 낮으면 중앙 SDG 버전 선언을 최신 배포로 맞추고 SDG·Common·Resource의 실제 해석과 소비 모듈 컴파일을 확인하세요.', 'Compose 설정과 문자열 리소스 정책은 저장소 지침을 따르세요.'],
        environment: {
          minSdk: { requiredBySource: catalog.setup.minSdk, supplied: args.environment?.minSdk ?? null,
            status: args.environment?.minSdk === undefined ? 'unknown' : args.environment.minSdk < catalog.setup.minSdk ? 'below_source_minimum' : 'meets_source_minimum',
            source: catalog.setup.source },
          kotlin: { supplied: args.environment?.kotlin ?? null, status: 'unknown' },
          compose: { supplied: args.environment?.compose ?? null, status: 'unknown' },
          reason: '소스의 최소 SDK 조건만 비교합니다. 소비 프로젝트의 Kotlin·Compose 호환성과 실제 Gradle 해석은 별도 확인해야 합니다. 카탈로그의 컴파일 환경은 available.verification에서 확인하세요.' } },
    };
    if (!evidence.length) return result(response('ok', { ...base, compatibility: 'unknown', nextSteps: ['최신 배포 버전의 API를 제공합니다. 적용 전 대상 모듈·variant의 SDG 의존성을 확인하세요.'] }, [catalog.setup.source]));
    if (!args.module || !args.variant) return result(response('unknown', { ...base, compatibility: 'unknown' }, [], ['소비 앱 호환성을 확인하려면 의존성 근거가 적용되는 module과 variant가 필요합니다.']));
    const priority = evidence.some(e => e.kind === 'resolved') ? 'resolved' : evidence.some(e => e.kind === 'source') ? 'source' : 'declared';
    const selected = evidence.filter(e => e.kind === priority);
    const identities = new Set(selected.map(e => `${e.version}:${e.sourceSha ?? ''}:${e.dirty ?? false}`));
    if (identities.size !== 1) return result(response('conflict', { ...base, compatibility: 'unknown', selected }));
    const target = selected[0]!;
    if (target.dirty || (target.kind === 'source' && target.sourceSha !== catalog.sourceSha)
        || (target.sourceSha && target.sourceSha !== catalog.sourceSha) || target.version !== catalog.version) {
      return result(response('version_mismatch', { ...base, compatibility: 'mismatch', selected }, [], ['최신 배포 버전의 API만 제공합니다. 입력한 의존성 또는 소스와 다르므로 적용 전 프로젝트의 SDG 업데이트와 컴파일 확인이 필요합니다.']));
    }
    const companionArtifacts = ['SDG-Android-Common', 'SDG-Android-Resource'];
    const companionEvidence = args.dependencyEvidence.filter(e => e.group === catalog.artifact.group && companionArtifacts.includes(e.artifact));
    const companionSelections = companionArtifacts.flatMap(artifact => {
      const candidates = companionEvidence.filter(e => e.artifact === artifact);
      const selectedKind = candidates.some(e => e.kind === 'resolved') ? 'resolved' : candidates.some(e => e.kind === 'source') ? 'source' : 'declared';
      return candidates.filter(e => e.kind === selectedKind);
    });
    if (companionSelections.some(e => e.version !== target.version || e.dirty || (e.sourceSha && e.sourceSha !== catalog.sourceSha))) {
      return result(response('version_mismatch', { ...base, compatibility: 'mismatch', selected, companionSelections }, [], ['최신 배포 버전의 API만 제공합니다. Common·Resource가 일치하지 않으므로 의존성을 확인하세요.']));
    }
    const warnings = target.kind === 'declared' ? ['선언 버전만 확인됐습니다. 실제 해석 결과는 미확인입니다.'] : [];
    if (companionArtifacts.some(artifact => !companionSelections.some(e => e.artifact === artifact && e.kind === 'resolved'))) warnings.push('Common·Resource의 전체 의존성 해석 근거가 없어 자산과 공통 API의 적용 버전은 추가 확인이 필요합니다.');
    if (evidence.some(e => e.version !== target.version)) warnings.push('하위 우선순위 근거와 버전이 다릅니다. 선택한 대상의 근거를 확인하세요.');
    return result(response(target.kind === 'resolved' ? 'resolved' : target.kind === 'source' ? 'source_snapshot' : 'declared_only', { ...base, compatibility: 'evidence_matches', selected, companionSelections }, [catalog.setup.source], warnings));
  }));

  server.registerTool('sdg_search_components', {
    description: '한글 목적 또는 Kotlin 이름으로 SDG component·template의 공개 Composable 후보를 찾습니다. category는 component·template 또는 패키지 구간입니다. 본 레포 kit은 ui/sdg_kit에서 직접 검색하세요. 검색 결과는 요약이며 호출 전 get_component에서 정확한 오버로드를 확인하세요.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({ catalogId: catalogField, query: z.string().trim().min(1).max(300), category: z.string().max(100).optional(), ...pageFields }).strict(),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const error = checkCatalog(args.catalogId); if (error) return result(error);
    const ranked = catalog.components.filter(c => !args.category || c.id.includes(`.${args.category}.`))
      .map(c => ({ ...summary(c), match: score(args.query, c.name, `${c.id} ${c.documentation ?? ''}`) }))
      .filter(c => c.match.score > 0).sort((a, b) => b.match.score - a.match.score || Number(a.deprecated) - Number(b.deprecated) || `${a.componentId}:${a.overloadId}`.localeCompare(`${b.componentId}:${b.overloadId}`));
    const batch = page(ranked, args.limit, args.cursor, `components:${args.query}:${args.category ?? ''}`);
    return result({ ...response(ranked.length ? 'ok' : 'not_found', { components: batch.items, total: batch.total, coverage: { overloads: catalog.coverage.overloads, excluded: catalog.coverage.excluded.length, typeResolution: catalog.coverage.typeResolution } }, batch.items.map(c => c.source)), nextCursor: batch.nextCursor, truncated: batch.truncated });
  }));

  server.registerTool('sdg_get_component', {
    description: '정확한 Kotlin 시그니처, receiver, 인자, 기본값, 관련 타입과 사용 예제 ID를 반환합니다. 여러 오버로드가 있으면 선택지를 반환합니다. typeId로 큰 관련 타입을 별도 조회할 수 있습니다.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({ catalogId: catalogField, componentId: z.string().min(1).max(500).optional(), overloadId: z.string().min(1).max(100).optional(), typeId: z.string().min(1).max(500).optional() }).strict()
      .refine(a => Number(!!a.componentId) + Number(!!a.typeId) === 1 && !(a.typeId && a.overloadId), 'componentId 또는 typeId 중 하나만 지정하고 overloadId는 componentId와 함께 사용하세요.'),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const error = checkCatalog(args.catalogId); if (error) return result(error);
    if (args.typeId) {
      const type = catalog.types.find(t => t.id === args.typeId);
      return result(response(type ? 'ok' : 'not_found', { type: type ?? null }, type ? [type.source] : []));
    }
    const matches = catalog.components.filter(c => c.id === args.componentId && (!args.overloadId || c.overloadId === args.overloadId));
    if (!matches.length) return result(response('not_found', { componentId: args.componentId, overloadId: args.overloadId }));
    if (matches.length > 1) return result(response('ambiguous', { overloads: matches.map(c => ({ ...summary(c), signature: c.signature })) }, matches.map(c => c.source)));
    const component = matches[0]!;
    const related = relatedTypes(component);
    const dependencies = component.sourceImports.some(i => i.startsWith('import org.joda.') && component.referencedTypes.some(t => i.endsWith('.' + t)))
      ? ['net.danlew:android.joda:2.12.5'] : [];
    return result(response('ok', { component, relatedTypes: related.map(t => ({ typeId: t.id, name: t.name, source: t.source })),
      examples: exampleSummaries([component]), typeMatching: 'imports_and_package',
      consumerRequirements: { dependencies, optIns: component.annotations.filter(a => a.startsWith('@OptIn')) } }, [component.source],
      ['예제는 import와 인자 구성을 대조한 후보입니다. Kotlin 오버로드 의미 분석 결과는 아닙니다.']));
  }));

  server.registerTool('sdg_get_example', {
    description: 'Kotlin 예제와 문맥을 반환합니다. compile_verified는 전체 파일의 소비 환경 컴파일, source_context_compiled는 원본 모듈에서의 컴파일입니다. 런타임 검증은 아닙니다.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({ catalogId: catalogField, exampleId: z.string().min(1).max(500) }).strict(),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const error = checkCatalog(args.catalogId); if (error) return result(error);
    const example = [...catalog.curatedExamples, ...catalog.examples].find(e => e.id === args.exampleId);
    return result(response(example ? 'ok' : 'example_unavailable', { example: example ?? null }, example ? [example.source] : [], example ? [example.origin === 'curated' ? '소비 프로젝트용 전체 파일 예제입니다. verification과 verifiedAgainst를 확인하고 앱의 상태·이벤트·문자열 정책에 맞춰 연결하세요. 런타임 동작은 검증하지 않았습니다.' : '원본 모듈의 보조 선언·리소스가 필요한 예제 단편입니다. source_context_compiled는 단편의 독립 컴파일을 의미하지 않습니다.'] : []));
  }));

  server.registerTool('sdg_search_assets', {
    description: '색상, typography, 간격, radius 또는 아이콘의 정확한 Kotlin 참조를 검색합니다. assetId로 한 항목을 조회하고, 검색어를 생략하면 페이지 목록을 반환합니다. 값은 원본 표현식입니다.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({ catalogId: catalogField, kind: assetKind, query: z.string().max(300).optional(), assetId: z.string().min(1).max(500).optional(), ...pageFields }).strict()
      .refine(a => !(a.query !== undefined && a.assetId !== undefined), 'query와 assetId는 함께 지정할 수 없습니다.'),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const error = checkCatalog(args.catalogId); if (error) return result(error);
    const ranked = catalog.assets.filter(a => a.kind === args.kind && (!args.assetId || a.id === args.assetId))
      .map(a => ({ ...a, match: args.query ? score(args.query, a.name, `${a.id} ${a.documentation ?? ''} ${JSON.stringify(a.valueExpression ?? '')}`) : { score: 1, matched: [] } }))
      .filter(a => a.match.score > 0).sort((a, b) => b.match.score - a.match.score || a.id.localeCompare(b.id));
    const batch = page(ranked, args.limit, args.cursor, `assets:${args.kind}:${args.query ?? ''}:${args.assetId ?? ''}`);
    return result({ ...response(ranked.length ? 'ok' : 'not_found', { assets: batch.items, total: batch.total }, batch.items.map(a => a.source)), nextCursor: batch.nextCursor, truncated: batch.truncated });
  }));

  server.registerTool('sdg_get_recipe', {
    description: '입력·선택·팝업·탭·목록 상태의 UI 조합 안내를 조회합니다. recipeId 생략 시 목록을 반환합니다. 조합 안내는 제품 정책이나 완성 화면 코드가 아닙니다.',
    annotations, outputSchema: responseSchema,
    inputSchema: z.object({ catalogId: catalogField, recipeId: z.string().min(1).max(100).optional() }).strict(),
  }, async args => inCatalog(async () => {
    const catalog = currentCatalog();
    const error = checkCatalog(args.catalogId); if (error) return result(error);
    const versionRecipes = catalog.guidance?.recipes ?? recipes;
    if (!args.recipeId) return result(response('ok', { recipes: versionRecipes.map(r => ({ recipeId: r.id, title: r.title, guidanceStatus: 'agent_source_reviewed' })) }));
    const recipe = versionRecipes.find(r => r.id === args.recipeId);
    if (!recipe) return result(response('not_found', { recipeId: args.recipeId }));
    const curated = catalog.curatedExamples.find(e => e.id === recipe.exampleId);
    const components = catalog.components.filter(c => (recipe.componentNames as readonly string[]).includes(c.name) && !c.deprecated);
    const missing = recipe.componentNames.filter(name => !components.some(c => c.name === name));
    return result(response('ok', { recipe: { ...recipe, guidanceStatus: 'agent_source_reviewed', components: components.map(summary),
      examples: exampleSummaries(components), unavailableComponents: missing, exampleId: curated?.id, exampleVerification: curated?.verification, implementationStatus: curated?.verification === 'compile_verified' ? 'compile_verified_example' : 'example_pending_verification' } }, components.map(c => c.source),
    ['조합 코드는 소스 검토 기준이며 앱 실행·접근성 검증과 Android 담당자의 승인을 의미하지 않습니다. 제품 정책은 작업 저장소에서 확인하세요.', ...(missing.length ? [`이 카탈로그에 없는 후보: ${missing.join(', ')}`] : [])]));
  }));
}
