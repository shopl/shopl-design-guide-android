// Execution policy belongs to the MCP program, not the released SDG catalog.
export const referenceBoundary = 'MCP 결과는 최신 배포 SDG의 참조이며 현재 작업 브랜치의 코드가 아닙니다. SDG 자체 개발은 실제 체크아웃 소스가 기준입니다. 카탈로그에 없거나 배포 API와 다르다는 이유만으로 미배포 API를 없다고 판단하거나 삭제·되돌리지 마세요.';
export const sourceWorkflow = 'SDG 자체의 컴포넌트·샘플 개발은 대상 저장소 AGENTS.md와 .agents/skills/sdg-builder-rule/SKILL.md를 우선 따르세요. Builder가 Git·빌드·PR을 관리하면 내부 AI는 허용된 Kotlin 소스만 수정합니다. 소비 앱용 의존성 업데이트·직접 Gradle 실행·앱 전용 TODO(resource-owner)를 확대 적용하지 마세요. 문자열·리소스 정책은 대상 저장소 지침을 따르세요. 컴포넌트 PR이나 Builder 샘플 빌드에 MCP 생성을 추가하지 않습니다.';
export const consumerWorkflow = '소비 앱 개발에서는 SDG component·template·앱의 기존 kit을 우선 재사용하세요. 앱이 이전 버전이면 저장소 지침과 허용 범위 안에서 중앙 SDG 의존성을 최신 배포로 맞추고 실제 의존성 해석·소비 모듈 컴파일을 확인하세요. 실행 주체와 Gradle 실행 권한, 문자열·리소스 정책은 대상 저장소 지침을 따르세요.';
export const serverInstructions = [
  '이 MCP와 함께 제공되는 Skill은 배포된 SDG를 사용하는 소비 앱 개발용 읽기 전용 참조 도구입니다.',
  referenceBoundary, sourceWorkflow, consumerWorkflow,
  'sdg_resolve_context의 workContext는 기본 consumer_app이며 SDG 자체 개발에서 배포 참조가 필요하면 sdg_source를 지정하세요. 이 값은 현재 요청의 안내만 구분하며 체크아웃을 읽거나 작업 권한을 부여하지 않습니다. 다른 도구도 항상 배포 카탈로그만 조회합니다.',
  '도구 호출 전에 원격 SDG 정보를 자동 확인합니다. update.state가 current가 아니면 실패 사유와 사용 중인 버전을 알리고 최신 여부를 단정하지 마세요. client_update_required일 때만 MCP 프로그램 갱신이 필요합니다. catalogId는 생략할 수 있습니다.',
  '소비 앱의 component·template는 sdg_search_components에서, kit은 대상 저장소 선언과 호출부에서 확인하세요. Shopl 앱 kit 경로는 ui/sdg_kit입니다. 정확한 오버로드와 import를 조회하세요.',
  '예제의 verification과 verifiedAgainst를 확인하세요. source_context_compiled는 배포 소스의 원본 문맥, compile_verified는 명시된 소비 환경의 전체 파일 컴파일입니다. 현재 브랜치·런타임 검증이나 제품 정책 승인을 의미하지 않습니다.',
].join(' ');
