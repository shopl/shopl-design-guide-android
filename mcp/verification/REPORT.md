# Android SDG MCP 검증 결과

검증일: 2026-10-26 · SDG: **1.26.0** · catalogId: `sdg-android:1.26.0:2753de67bba13d7a`

배포 SHA: `6b2ed256ac7ba7a03c4a8b6fc9ae4cfc865ff8e2` · [공식 PUBLISHED 배포](https://github.com/shopl/shopl-design-guide-android/actions/runs/40254557428)

## 소비 앱 / SDG 자체 개발 범위 검토

이 변경은 SDG Builder와의 사전 충돌 예방입니다. 실제 Builder 빌드 실패가 재현된 것은 아닙니다.

- 명시된 요구사항 기반 변경: 세 Skill의 발견 설명과 본문을 소비 앱 개발로 한정했습니다. SDG 자체 개발에는 저장소 AGENTS.md·Builder 규칙과 실제 체크아웃을 우선하며, 내부 AI의 Kotlin 변경 범위와 Builder의 Git·빌드·PR 관리를 명시했습니다. 문자열·리소스 정책은 대상 저장소에 위임했습니다. 서버 instructions와 모든 도구 응답에도 배포 참조와 현재 브랜치의 구분을 적용했습니다.
- 추론·해석으로 구현한 부분: `sdg_resolve_context`에 요청별 `workContext`를 추가했습니다. 기본 `consumer_app`은 기존 비교 흐름을 유지합니다. `sdg_source`는 소비 앱용 setup을 반환하지 않고 `compatibility: not_assessed`, `checkoutInspected: false`로 로컬 소스를 검사하지 않았음을 표시합니다. 세션 모드나 API 조회 대상을 바꾸지는 않습니다. 공통 정책은 `src/usage-policy.ts`에서 관리합니다.
- 검증: TypeScript 검사·빌드, Skill 3개 형식 검사, 독립 설치 플러그인 144회 호출, 개발 서버/번들 × 두 프로토콜의 사용 목적별 84회 호출이 통과했습니다. 소비 앱의 기본 조회·구버전 경고·해석 근거·component/template 재사용 흐름과 SDG 자체 개발의 다른 버전·SHA·dirty 근거, 미배포 후보 조회 실패 시 체크아웃 우선 안내, 요청 간 목적 격리를 확인했습니다. 의존성·미배포 소스 근거는 진단용 가상 입력입니다.
- 생성물: 기존 `plugin:pack` 절차로 실행 번들·release.json 파일 해시·설치 압축 파일과 SHA-256을 갱신했습니다. 재생성 해시 일치를 확인했습니다.
- 유지: 정식 배포 후 데이터 동기화, CI·빌드 스크립트, Builder 규칙, Kotlin 소스와 배포 카탈로그는 변경하지 않았습니다. catalogId와 검증 receipt도 동일합니다. 새 Android 컴파일·Builder 내부 AI 실행·샘플 빌드·호스트 앱 UI 검증은 하지 않았습니다. 제품 레이어 하네스는 대상이 없습니다.

## 이번 축소 후 재검증

| 항목 | 결과·범위 |
| --- | --- |
| 전체 검증 | `npm run verify --prefix mcp` 종료 코드 0. 추출·Maven 대조·원본 샘플·소비 코드 컴파일·두 프로토콜 진단 |
| 정보 보존 | 새 추출 원본과 저장본 복원 결과 전체 일치. 이전 구현과 검증 시각·로그 근거를 제외한 모든 사실 일치 |
| 저장 형식 | 인코딩→복원 동일성 확인. 잘못된 버전·참조·테이블 등 5개 입력 거부 |
| 포함 범위 | API 194개, 타입 370개, 에셋 301개, 원본 예제 245개, 조합 예제 6개 유지 |
| 플러그인 | 세 호스트 실행 선언 × 두 MCP 프로토콜, 도구 호출 총 144회 통과. 별도 경로에서 독립 실행 |
| 기존 설치 호환 | 축소 전부터 실행한 reader 1 MCP의 같은 프로세스에서 새 공개 데이터 다운로드·적용, `current` 확인 |
| 자동 갱신·복구 | Node 22에서 두 프로토콜 각각 14개 항목 통과. 격리 로컬 Git 게시와 실제 MCP 호출 |
| 재생성 | 다시 빌드한 플러그인 파일 해시가 모두 동일 |
| Workflow | MCP workflow actionlint 통과. 기존 배포 workflow는 기존 ShellCheck 지적을 제외한 actionlint 통과 |
| 레이어 하네스 | 대상 없음. Shopl 제품 data/domain/features/ui 소스 변경 없음 |

자동 갱신·복구에는 실제 과거 검증본 1.23.2→1.26.0 전환, 중복 다운로드 생략, 이전 catalogId 거부, component/template 조회, 네트워크·파일 손상·reader 비호환·원격 rollback, 오프라인 재시작·캐시 손상·재접속, 게시자의 과거 버전 거부·동일 payload 무변경을 포함합니다. 공식 GitHub에 데이터를 게시한 검증은 아닙니다.

컴파일 환경은 JDK 21, AGP 8.10.0, Kotlin 2.1.20, compileSdk 36, minSdk 28, Compose BOM 2025.05.01입니다. 원본 예제는 원래 모듈 문맥, 조합 예제는 외부 소비 프로젝트의 전체 파일로 검증합니다. 이 성공이 모든 Kotlin/Compose 버전·화면 동작·접근성을 보장하지 않습니다.

## 이전에 확인한 사용자 경로

- 실제 1.26.0 배포 후 배포 전부터 실행한 MCP가 코드 수정·재설치·재시작 없이 1.25.2→1.26.0으로 갱신됐습니다. 로컬 게시 환경에서 도구 호출 40회, 추가 시그니처 6개·타입 11개와 제거 시그니처 4개를 확인했습니다.
- 실제 Codex App Server에서 플러그인 연결·6개 도구·스킬 발견을 확인했습니다. Claude·Cursor는 실행 선언과 MCP 통신 검증이며 앱 UI 검증은 아닙니다.
- 보완한 본 레포 지침 아래 일반 UI 요청에서 에이전트가 MCP를 자발적으로 호출하고 `SDGEmpty`를 적용한 사례 1회가 통과했습니다. Preview 문자열은 검토자가 보완했습니다. 다른 모델·클라이언트의 반복 성공률을 의미하지 않습니다.
- 별도 본 레포 worktree에서 당시 최신 SDG 1.25.2 의존성 해석과 ui/workplace 컴파일을 확인했습니다. 이 검증용 지침과 UI는 본 레포에 게시하지 않았습니다.

## 명시된 요구사항 기반 변경

“전부 개선해서 축소” 요청에 따라 중복 카탈로그를 한 벌로 통합하고 반복 메타데이터와 누적 보고서를 정리했습니다. 최신 버전만 제공, component·template·kit 우선 사용, 배포 시점 동기화, 사용자 수동 조작 없는 데이터 갱신은 유지합니다.

## 추론·해석으로 구현한 부분

- 저장본은 공통 소스·import·기본값을 공유하고 로더에서 복원합니다. 공개 feed는 기존 schema 1로 내보내 기존 설치본의 호환성을 유지합니다. API·타입·예제의 내용·개수 제한은 추가하지 않았습니다.
- 상세 실행 기록은 `.cache/runs/`와 CI artifact에 보관하고 저장소에는 현재 요약만 남깁니다. 과거 보고서는 Git 이력에 보존됩니다.
- CI의 폐기된 SDK `tools` 설치 실패를 해결하기 위해 필요한 `platform-tools`를 명시했습니다.

## 재현과 운영 인수

`npm run verify --prefix mcp`, `npm run plugin:pack --prefix mcp`, `npm run feed:prepare --prefix mcp` 순으로 실행합니다. 실행별 원본 카탈로그·컴파일·진단·변경 비교 로그는 `.cache/runs/`에 생성되며 CI artifact로 보관됩니다. 요약 근거는 [result.json](result.json)에 있습니다. 영구 테스트 파일·Android 문자열 리소스·기기 실행은 추가하지 않았습니다.

공식 GitHub Actions 게시와 기본 공개 URL의 자동 갱신은 **병합 후 운영 검증 항목**입니다. 조직의 GITHUB_TOKEN 데이터 브랜치 쓰기 정책도 실제 실행으로 확인해야 합니다.

1. 동기화 workflow에 위 버전·SHA를 전달해 최초 데이터를 게시합니다.
2. 공개 `latest.json`의 버전·SHA·파일 해시와 사용자 MCP의 `update.state=current`를 확인합니다.
3. 다음 실제 SDG 배포에서 PUBLISHED→자동 검증·게시→이미 실행 중인 MCP 갱신 전체를 확인합니다.
