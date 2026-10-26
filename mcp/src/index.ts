import { McpServer } from '@modelcontextprotocol/server';
import { serveStdio } from '@modelcontextprotocol/server/stdio';
import { readFileSync } from 'node:fs';
import { loadCatalogs } from './catalog.js';
import { registerTools } from './tools.js';

// The distributable bundle has no parent package.json. Development builds retain the package fallback.
declare const __SDG_MCP_VERSION__: string;
const version = typeof __SDG_MCP_VERSION__ === 'string' ? __SDG_MCP_VERSION__
  : JSON.parse(readFileSync(new URL('../package.json', import.meta.url), 'utf8')).version;
loadCatalogs();
serveStdio(() => {
  const server = new McpServer({ name: 'sdg-android', version }, {
    instructions: '도구 호출 전에 원격 SDG 정보를 자동 확인하고 검증된 최신 정보 하나를 제공합니다. 응답 update.state가 current가 아니면 최신 여부를 단정하지 말고 실패 사유와 사용 중인 버전을 알리세요. client_update_required일 때만 MCP 프로그램 갱신이 필요합니다. sdg_resolve_context에서 최신 버전과 설정을 확인하세요. catalogId는 생략할 수 있습니다. 소비 앱의 버전이 다르면 최신 API 적용 전 의존성 업데이트와 컴파일 확인이 필요합니다. SDG component·template와 본 레포 kit을 우선 재사용하세요. component·template는 sdg_search_components에서 검색하고 kit은 대상 저장소 ui/sdg_kit의 component·template와 호출부를 직접 검색하세요. 맞는 kit 조합, 공통 template, 기본 component를 사용하고 기존 공용 UI도 확인하세요. 버전 차이가 있으면 작업 저장소의 중앙 SDG 버전 선언을 최신 배포로 맞추고 실제 의존성 해석과 소비 모듈 컴파일을 확인하세요. API를 추측하지 말고 정확한 오버로드와 import를 조회하세요. 이 서버는 읽기 전용입니다. 예제의 verification과 verifiedAgainst를 확인하세요. source_context_compiled는 원본 문맥, compile_verified는 명시된 소비 환경의 전체 파일 컴파일입니다. 런타임 검증이나 제품 정책 승인을 의미하지 않습니다.',
  });
  registerTools(server);
  return server;
}, { onerror: error => console.error(error.message) });
