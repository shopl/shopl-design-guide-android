# Android SDG MCP

비 Android 개발자의 에이전트가 실제 SDG API와 기존 UI를 재사용하도록 돕는 로컬 MCP입니다. **최초 연결 이후 도구 호출 전에 최신 SDG 정보를 직접 확인하고 자동으로 내려받습니다.** 사용자가 SDG 업데이트마다 플러그인을 갱신할 필요는 없습니다. 조회 가능한 버전은 하나이며 과거 버전 선택 기능은 없습니다.

프로그램·스킬의 최초 설치는 [플러그인 안내](../plugins/sdg-android/README.md)를 따릅니다. Node.js 22 이상이 필요합니다. 포함된 초기 정보는 1.26.0이며, 원격 갱신 후에는 더 최신 정보로 바뀝니다. 현재 공식 workflow 반영·최초 데이터 게시와 실제 새 SDG 배포에 대한 운영 검증은 남아 있습니다.

## 실행과 자동 갱신

```sh
npm ci --prefix mcp
npm run build --prefix mcp
node mcp/dist/index.js
```

MCP는 각 도구 호출 전에 GitHub의 `sdg-mcp-data/latest.json`을 확인합니다. 변경된 경우 데이터 파일을 내려받아 SHA-256·크기·형식·배포 식별자·검증 근거를 대조한 뒤 적용합니다. 실행 중인 서버에서도 다음 호출부터 새 정보를 사용합니다. 한 요청 내에서는 같은 스냅샷과 갱신 상태를 유지합니다. 동시 요청의 네트워크 조회는 합칩니다.

기본 URL: `https://raw.githubusercontent.com/shopl/shopl-design-guide-android/sdg-mcp-data/latest.json`

별도 상시 서버나 사용자 GitHub 인증은 필요 없습니다. 공개 JSON만 읽으며 실행 코드를 자동 다운로드하거나 실행하지 않습니다. 네트워크가 없거나 게시가 실패하면 마지막 검증본을 사용하고 모든 도구 응답의 `update`와 warnings에 실패를 표시합니다. 설치된 프로그램의 데이터 형식 지원 범위를 넘으면 client_update_required를 반환하고 기존 정보를 유지합니다.

| update.state | 의미 |
| --- | --- |
| current | 게시 경로의 최신 검증 정보와 일치 확인 |
| offline | 다운로드·검증 실패. 마지막 검증본 또는 초기 포함본 사용 |
| remote_behind | 원격의 이전 버전/게시 정보를 거부하고 기존 정보 유지 |
| client_update_required | 새 데이터 형식에 맞는 MCP 프로그램 필요 |
| disabled | 명시적 고정 설정 또는 배포 검증 진단 중 |

current는 아직 게시 검증 중인 Maven 배포까지 반영했다는 뜻은 아닙니다. SDG 배포→검증→GitHub 게시·전파에 시간이 필요합니다. 서버를 바꿀 필요 없이 SDG API·타입·토큰·아이콘·예제·검색 안내를 데이터로 갱신합니다. 추출기가 지원하지 않는 새로운 소스 구조나 데이터 형식은 유지보수 작업이 필요합니다.

로컬 저장은 `~/.cache/sdg-android-mcp/<게시주소 해시>/`의 불변 스냅샷입니다. 여러 세션이 동시에 실행돼도 파일을 원자적으로 추가하고 가장 최신의 유효한 검증본을 선택합니다. 손상된 저장본은 건너뛰며 과거 버전을 API로 제공하지 않습니다.

진단·관리용 환경 변수:

- `SDG_CATALOG_URL`: 다른 신뢰할 수 있는 HTTPS 게시 주소. 로컬 진단에만 loopback HTTP 허용.
- `SDG_CATALOG_CACHE_DIR`: 로컬 저장 경로 변경.
- `SDG_CATALOG_UPDATES=off`: 원격 갱신 없이 포함된 정보만 진단.
- `SDG_CATALOG_REGISTRY`: 기존 고정 registry 진단. 원격 갱신도 비활성화됨.

일반 사용자에게는 환경 변수 설정이 필요 없습니다. 각 HTTP 요청의 시간 제한은 4초, manifest는 64 KiB, 데이터는 16 MiB입니다. 실패 시 최신 상태로 표시하지 않습니다.

## 도구와 사용 순서

| 도구 | 용도 |
| --- | --- |
| `sdg_resolve_context` | 최신 카탈로그·설정 안내와 소비 앱 의존성 차이 확인 |
| `sdg_search_components` | 한글 목적·Kotlin 이름으로 SDG component·template 검색 |
| `sdg_get_component` | 오버로드·receiver·인자·관련 타입·의존성·opt-in 확인 |
| `sdg_get_example` | 원본 및 조합 예제와 검증 범위 조회 |
| `sdg_search_assets` | 색상·typography·간격·radius·drawable 조회 |
| `sdg_get_recipe` | 입력·선택·팝업·탭·목록 상태 조합 안내 |

1. 소비 저장소 지침과 대상 모듈·variant를 확인합니다. SDG component·template는 MCP에서 조회하고, 본 레포 kit은 `ui/sdg_kit`의 component·template 및 호출부를 직접 검색합니다. 맞는 kit 조합·template·component와 기존 공용 UI를 우선 재사용합니다.
2. `sdg_resolve_context`를 입력 없이 호출하여 최신 버전과 설정을 확인합니다. 소비 앱과 비교하려면 모듈·variant 및 SDG·Common·Resource 의존성 근거를 전달합니다.
3. 컴포넌트와 타입·예제를 조회합니다. `catalogId`는 생략할 수 있습니다. 같은 스냅샷인지 확인하려면 반환된 ID를 전달합니다.
4. 소비 앱의 SDG 버전이 낮으면 중앙 버전 선언을 최신 배포로 맞춥니다. SDG·Common·Resource의 실제 의존성 해석을 확인하고 앱의 상태·이벤트·문자열 리소스 정책에 맞게 구현합니다.
5. 소비 저장소의 컴파일과 변경 레이어 리뷰를 수행합니다.

```json
{
  "module": ":features:user",
  "variant": "devDebug",
  "dependencyEvidence": [{
    "group": "com.shoplworks",
    "artifact": "SDG-Android",
    "version": "1.23.2",
    "kind": "declared",
    "source": "gradle/libs.versions.toml:128"
  }]
}
```

`declared`는 선언만 확인한 상태입니다. 실제 Gradle 해석을 확인한 경우에만 `resolved` 근거를 전달합니다. 서버가 소비 저장소 파일이나 Gradle을 실행하여 근거를 검증하지는 않습니다. `source` 근거는 전체 `sourceSha`가 필요하며 변경 중인 소스는 `dirty: true`로 전달합니다.

예시처럼 소비 앱이 이전 버전이면 `version_mismatch`와 최신 카탈로그를 반환합니다. 이는 이전 버전과의 호환성을 보장하지 않습니다. 적용 전 SDG 의존성 업데이트와 소비 앱 컴파일을 확인해야 합니다. Common·Resource의 근거가 없으면 경고하고, 버전·SHA가 다르거나 소스가 수정 중이면 차이를 알립니다. 이전 `catalogId`를 명시하면 `catalog_unavailable`과 context 재조회 안내를 반환합니다. ID는 버전 선택이 아닌 스냅샷 확인 용도입니다. Kotlin·Compose 전체 호환 범위는 아직 검증하지 않았습니다.

검색은 기본 5개·최대 20개이며 cursor는 같은 카탈로그와 검색 조건에서만 유효합니다. 예제와 타입 연결은 import·package·인자 구성을 대조한 후보이며 완전한 Kotlin 의미 분석은 아닙니다.

## SDG 원격 배포와 자동 게시

동기화 시점은 기존 Maven 배포 workflow가 확인한 **Sonatype PUBLISHED**입니다. 배포자를 구분하지 않고 같은 공식 배포 경로를 사용하면 동작합니다. 공식 workflow 밖에서 수동으로 Maven에 올리는 경우는 자동 감지하지 않으며 workflow_dispatch로 실제 버전·SHA를 전달해 재검증합니다.

```text
PUBLISHED + 실제 배포 SHA·버전
 → Maven의 세 모듈 소스·리소스 대조
 → 원본 샘플·별도 소비 코드 컴파일
 → 후보 MCP와 플러그인 진단
 → 읽기 전용 검증 job이 데이터 artifact 생성
 → 별도 게시 job이 sdg-mcp-data 브랜치에 원자적 commit/push
 → 사용자 MCP의 다음 도구 호출에서 자동 확인·적용
```

- auto-deploy.yml이 실제 배포 정보로 mcp-sync-release.yml을 호출합니다.
- 검증 job은 contents: read, 게시 job만 contents: write입니다. main 등 소스 브랜치, PR, 태그, npm은 바꾸지 않습니다. 배포마다 데이터 게시를 위해 PR을 만들 필요가 없습니다.
- 게시 스크립트는 이전 버전 이벤트와 동일 버전의 sourceSha 변경을 거부합니다. 일반 push가 충돌하면 실패하며 강제 push를 하지 않습니다. workflow 전체를 직렬화합니다.
- 데이터는 콘텐츠 해시 파일명으로 게시합니다. latest.json과 새 파일은 한 Git commit에 반영합니다. 이미 manifest를 받은 클라이언트의 다운로드가 깨지지 않도록 과거 해시 파일은 브랜치에 남겨두지만 버전 선택 기능은 제공하지 않습니다.
- 추출·대조·컴파일·진단·게시 실패 시 기존 게시 정보가 유지됩니다. API/타입/에셋 변경 기록과 로그·설치 파일도 artifact로 보관합니다.
- GitHub 조직 정책에서 GITHUB_TOKEN의 데이터 브랜치 쓰기를 허용해야 합니다. 실제 운영 실행은 아직 확인하지 않았습니다.

로컬 동기화에는 JDK 21, Android SDK 36과 실제 소스 commit이 필요합니다.

```sh
npm run sync --prefix mcp -- --version 1.26.0 --source-sha 6b2ed256ac7ba7a03c4a8b6fc9ae4cfc865ff8e2
npm run plugin:pack --prefix mcp
npm run feed:prepare --prefix mcp
# 아래 명령은 실제 origin의 sdg-mcp-data 브랜치에 게시합니다. 운영 담당자/CI용입니다.
npm run feed:publish --prefix mcp
```

동기화 잠금이 남으면 실행 중인 동기화가 없는지 확인한 뒤 mcp/.cache/sync.lock을 정리합니다. 데이터 게시에는 별도 npm 배포나 클라우드 컴퓨터가 필요 없습니다.

## MCP 업데이트와 검증

```sh
npm run verify --prefix mcp
npm run doctor --prefix mcp
```

`verify`는 빌드, 현재 최신 카탈로그의 소스·아티팩트·컴파일 재검증(`--current`), 후보 MCP 진단, 최종 최신 버전 MCP 진단을 수행합니다. `doctor`는 현재 빌드된 서버의 연결과 도구 조회를 진단하며 Kotlin 컴파일을 다시 실행하지 않습니다. 테스트 파일이나 기기 실행은 포함하지 않습니다.

카탈로그 내용이 바뀌면 ID도 바뀝니다. 검증 시각·로그 해시만 바뀌면 ID는 유지됩니다. 검증 기록의 `verificationId`는 별도로 갱신합니다. 검색 별칭·조합 안내·예제는 카탈로그에 포함되므로 MCP 코드만 빌드한다고 안내가 바뀌지는 않습니다. 해당 내용을 수정한 경우 `verify`로 다시 생성·검증합니다.

MCP 서버 버전은 `package.json`에서 읽어 번들에 주입합니다. Codex 매니페스트도 같은 값으로 생성합니다. `plugins/sdg-android/release.json`에서 서버 버전·초기 포함 SDG 버전·배포 SHA·catalogId·파일 해시와 자동 갱신 주소를 구분합니다. 실제 응답은 원격에서 갱신한 더 최신 정보일 수 있습니다. 실제 MCP 릴리스 때 서버 버전과 변경 기록을 함께 관리합니다.

## 파일과 검증 범위

- `data/registry.json`: 제공 중인 최신 버전 한 개의 SHA·카탈로그 ID와 검증 revision.
- `data/<version>-<content-id>.json`: 현재 최신 배포의 API·타입·에셋·예제·검증 근거. 교체 후 이전 payload는 제거합니다.
- `data/changes/<version>.json`: 이전 카탈로그 대비 API·타입·에셋 변경 이력. 과거 카탈로그를 제공하지 않으며 변경 보고서만 보관합니다. 호환성 파괴 여부는 담당자가 검토합니다.
- `data/<version>-protocol.json`: 두 MCP 프로토콜 연결 진단 결과.
- `scripts/sync-release.mjs`: 후보 생성·검증·반영 조정.
- `verification/`: 배포 SDG를 소비하는 컴파일 프로젝트와 6개 예제.
- `.cache/runs/`: 실행별 소스·아티팩트·로그. Git 추적 대상이 아닙니다.

| SDG 버전 | 공개 API 오버로드 | 에셋 참조 | 원본 예제 | 전체 파일 조합 예제 |
| --- | ---: | ---: | ---: | ---: |
| 1.26.0 | 194 | 301 | 245 | 6 |

원본 예제의 `source_context_compiled`는 원래 모듈 문맥에서의 컴파일입니다. 조합 예제의 `compile_verified`는 명시된 소비 환경에서 전체 파일을 컴파일했다는 의미입니다. 화면 동작·접근성·담당자 승인·제품 정책 확정을 의미하지 않습니다.

[최신 버전 전용 전환 검증](verification/LATEST_ONLY_REPORT.md)을 참고하세요. [이전 동기화 보완 검증](verification/SYNC_REPORT.md)은 다중 버전을 제공하던 시점의 기록입니다. [초기 검증 기록](verification/REPORT.md)은 초기 단일 버전 구현 시점의 기록입니다. 실제 화면 연결, 기기 실행과 비 Android 개발자 파일럿은 남아 있습니다. 본 레포 컴파일과 자발적 MCP 사용의 제한된 검증 결과는 아래에 기록했습니다.

## 본 레포 재사용 지침 검증 (2026-10-26)

Shopl 본 레포 기준 SHA `1287ba3a2129a424bc439292a782660af4fb4b60`에서 별도 worktree `shopl-android-sdg-mcp-guidance`를 만들어 검증했습니다. 변경은 루트·features·ui의 AGENTS.md와 SDG 버전 선언입니다. 원래 체크아웃이나 원격 브랜치에는 아직 반영하지 않았습니다.

- 명시된 요구사항 기반 변경: component·template·kit 우선 재사용, MCP 조회 절차, 작업 에이전트의 최신 SDG 버전 정렬을 지침에 추가했습니다. 사전 확인 없는 PR 생성을 막기 위해 MCP 동기화 workflow의 자동 PR 생성 단계를 제거했습니다.
- 추론·해석으로 구현한 부분: 요구사항에 맞는 도메인 kit 조합, 공통 template, 기본 component 순으로 용도에 따라 선택하도록 구체화했습니다. 본 레포 kit은 MCP에 복제하지 않고 로컬 선언·호출부를 확인하게 했습니다. 당시에는 동기화 결과를 artifact로만 보관했습니다. 현재 자동 게시 구조는 위 운영 절차를 따릅니다.
- 본 레포 SDG를 1.23.2에서 공개 최신 1.25.2로 맞춘 후 `:ui:compileDevDebugKotlin`, `:features:workplace:compileDevDebugKotlin`이 종료 코드 0으로 통과했습니다. 두 모듈의 `devDebugCompileClasspath`에서 SDG·Common·Resource 모두 1.25.2로 해석됐습니다. 기존 kit과 소비 UI 소스를 수정하지 않고 컴파일했습니다.
- MCP 진단은 두 프로토콜에서 각각 24회 호출을 통과했습니다. component `SDGBoxButton`과 template `SDGInputCenterPopup`의 검색·상세 API, 로컬 kit 검색 안내를 확인했습니다.
- Codex App Server 실제 연결에서 위 API를 조회하고 이전 버전은 `version_mismatch`, 실제 의존성 해석 근거를 전달한 최신 버전은 `resolved`로 반환하는 것을 확인했습니다. 이 단계는 도구 직접 호출 검증입니다. 이후 별도 에이전트의 일반 UI 요청 평가를 아래와 같이 수행했습니다.
- TypeScript 빌드·검사, MCP workflow actionlint, 기존 배포 workflow의 shellcheck 제외 actionlint와 diff 검사를 통과했습니다. 원격 배포 이벤트·artifact 업로드·사용자 설치/업데이트 경로는 미검증입니다.
- 영구 테스트 파일, UI 코드, 문자열 리소스는 추가·수정하지 않았습니다. Shopl 레이어 하네스는 대상 코드 변경이 없어 적용 대상이 아닙니다. PR 생성·commit·push·배포는 실행하지 않았습니다.

검증 근거: [본 레포 컴파일·의존성](verification/evidence/shopl-reuse-validation.json), [MCP 연결 진단](verification/evidence/reuse-guidance-protocol.json), [Codex 실제 연결](verification/evidence/codex-reuse-guidance.json).

## 자발적 MCP 사용 검증 (2026-10-26)

새 Codex 세션에 “근무지 검색 결과가 없을 때 안내와 다시 검색 버튼을 표시하는 Compose UI 조각을 작성”하도록 요청했습니다. 요청문에는 SDG나 MCP 이름과 호출 순서를 넣지 않았습니다. 부모 대화·메모리를 전달하지 않았고, 보완된 본 레포 AGENTS.md와 로컬 `sdg-android` 등록을 사용했습니다. 다른 MCP 서버는 이 실행에서만 비활성화했습니다. 실행 방법은 [Codex 비대화형 모드 공식 문서](https://learn.chatgpt.com/docs/non-interactive-mode)를 참고했습니다.

- 모델은 로컬 설정의 `gpt-6-astra`, reasoning `high`를 사용했습니다. 모델을 별도로 지정하지 않았습니다.
- 에이전트가 코드 작성 전에 context → 검색 → 상세 API → 예제 → 에셋 → 실제 의존성 근거로 context 재확인 순서로 MCP를 6회 호출했습니다. context와 검색은 병렬 호출이므로 둘 사이의 엄격한 선후 관계를 의미하지 않습니다.
- 로컬 `ui/sdg_kit`과 소비 코드를 확인한 뒤, 이번 용도에는 SDG `template.empty.SDGEmpty`를 재사용했습니다. 빈 상태 안내·버튼 문구·클릭 콜백은 호출부에서 받는 코드를 작성했습니다.
- 최초 선언 근거에 `dirty: true`를 넣어 같은 버전에도 불일치 응답을 받았지만, 에이전트가 세 아티팩트의 실제 Gradle 해석을 확인하고 `resolved` 응답을 받았습니다. 추가 지시는 하지 않았습니다.
- 에이전트가 직접 실행한 `:features:workplace:compileDevDebugKotlin`과 새 파일을 포함한 UI 하네스는 종료 코드 0입니다.
- 별도 수동 검토에서 Preview 문구 하드코딩을 발견했습니다. 검토자가 기존 `R.string.cm_tryagain`을 사용하도록 보완하고 전용 “다시 검색” 키 TODO를 유지했습니다. 보완 후 컴파일과 UI 하네스도 종료 코드 0입니다. 이 보완은 에이전트가 스스로 수정한 결과로 계산하지 않습니다.
- 판정: **지침이 있는 저장소에서 사용자가 MCP를 별도로 언급하지 않아도 호출하고 API를 적용한 사례 1회 통과**. 지침 없는 발견, 다른 모델·클라이언트, 반복 성공률을 입증하지 않습니다. 컴파일 검증이며 기기 런타임이나 사용자 배포 검증이 아닙니다.
- 검증용 UI는 별도 `shopl-android-sdg-autonomy-eval` worktree에만 있고 제품 화면에 연결하지 않았습니다. 테스트·문자열 리소스 파일 수정, commit·push·PR·배포는 없습니다.

[요청문·호출 순서·검증 근거](verification/evidence/autonomous-use.json)에 원래 결과와 검토자 보완을 구분해 기록했습니다.

## 플러그인 패키징과 독립 설치 진단

```sh
npm run plugin:pack --prefix mcp
```

현재 검증된 카탈로그로 서버 의존성을 번들하고 스킬·매니페스트를 패키징합니다. 카탈로그를 새로 생성하는 명령은 `sync`/`verify`입니다. `plugin:pack`은 새 SDG 배포 검증을 대신하지 않습니다. 생성물은 `plugins/sdg-android/server/`, `release.json`, `mcp/.cache/distribution/`입니다. 서버·스킬·매니페스트 변경 후 다시 실행합니다. server와 release.json은 소스와 함께 리뷰하고 커밋합니다.

`plugin:doctor`는 플러그인만 공백·한글이 있는 임시 경로로 복사하고 파일 해시를 대조합니다. node_modules·개발용 package.json 없이 세 호스트 선언의 서버를 실행합니다. 두 프로토콜에서 6개 도구와 버전 불일치·오래된 catalogId 거부를 진단합니다. 호스트 앱 UI나 자동 업데이트까지 확인했다는 뜻은 아닙니다. `SDG_PLUGIN_REPORT`로 결과 JSON을 보관할 수 있습니다.

CI는 재생성 전후 server·release.json·Codex 매니페스트를 비교해 오래된 생성물을 차단합니다. 실제 배포 인수 확인은 다음 순서입니다.

1. 새 SDG의 PUBLISHED·sourceSha와 sync/publish job 성공 확인.
2. 공개 latest.json의 버전·SHA·파일 해시 확인.
3. 이미 실행 중인 MCP에서 재설치·재시작 없이 다음 도구 호출.
4. update.state=current 및 새 버전·catalogId와 component/template API 확인.
5. 앱의 최신 의존성 해석과 소비 모듈 컴파일 확인.

이 경로가 끝나기 전에는 원격 자동 업데이트가 운영 검증됐다고 보고하지 않습니다.

2026-10-26 로컬 리팩터링 검증은 `verification/evidence/plugin-refactor.json`에 기록했습니다. `plugin-codex-install.json`은 독립 설치 폴더와 실제 Codex App Server를 통한 연결·6개 도구·스킬 3개 발견 결과입니다. `plugin-git-update.json`은 로컬 HTTP Git 원격에서 새 커밋을 가져와 설치 캐시를 갱신한 결과입니다. 동일 SDG 1.25.2의 검증 기록 갱신으로 확인했으며, 실제 새 SDG 배포나 공식 GitHub의 운영 검증을 대신하지 않습니다.

## 직접 자동 갱신 검증

`verification/evidence/automatic-data-update.json`: 과거 실제 검증한 1.23.2 포함본에서 시작해 로컬 Git 원격의 실제 게시 스크립트로 1.25.2를 게시했습니다. 같은 MCP 프로세스의 다음 호출에서 새 데이터와 component/template API를 확인했습니다. 재다운로드 생략, 이전 catalogId 거부, 네트워크 실패, 파일 손상, reader 비호환, 원격 rollback 거부, 오프라인 재시작·손상된 캐시 복구·재접속, 게시자의 이전 버전 거부도 확인했습니다. 임시 localhost 환경 검증이며 공식 GitHub 게시나 새 실제 SDG 배포 완료를 의미하지 않습니다. 이전 plugin-refactor.json과 plugin-git-update.json은 클라이언트의 플러그인 갱신 방식 검증 기록입니다.

Node 22와 MCP 2026-07-28 프로토콜의 같은 자동 갱신·복구 경로는 `verification/evidence/automatic-data-update-node22.json`에 기록했습니다.

## 실제 1.26.0 배포 후 갱신 검증 (2026-10-26)

[공식 배포 실행](https://github.com/shopl/shopl-design-guide-android/actions/runs/40254557428)의 실제 checkout SHA `6b2ed256ac7ba7a03c4a8b6fc9ae4cfc865ff8e2`와 PUBLISHED 응답을 확인했습니다. SDG·Common·Resource의 Maven Central 최신 버전은 모두 1.26.0입니다.

- 배포된 소스와 JSON 추출 결과를 대조하고 원본 샘플, 공개 API 194개, 에셋 301개와 조합 예제 6개의 소비 코드 컴파일을 통과했습니다.
- 배포 전에 실행한 Node 22 MCP 프로세스를 그대로 유지했습니다. MCP 프로그램 수정·재설치·재시작 없이 1.25.2에서 1.26.0 데이터를 가져왔고 update.state=current를 확인했습니다. 새 payload 다운로드는 1회였습니다.
- 새 SDGEmptyIcon·SDGTextNavi 검색, 추가된 시그니처 6개와 타입 11개 조회, 제거된 시그니처 4개와 이전 catalogId 거부를 확인했습니다. 6개 도구를 총 40회 호출했습니다.
- 게시 스크립트는 격리한 로컬 Git 원격에 실행했습니다. 데이터 생성과 게시 시작은 검증자가 수행했습니다. 공식 GitHub Actions가 자동으로 게시한 결과나 공식 데이터 URL의 운영 검증으로 해석하지 않습니다.
- 명시된 요구사항 기반 변경: 실제 새 배포의 최신 카탈로그·최초 설치용 생성물과 현재 안내를 갱신하고, 실행 중인 MCP의 자동 데이터 적용을 검증했습니다.
- 추론·해석으로 구현한 부분: 이번 검증에서 새 제품 동작은 추가하지 않았습니다. 앞서 준비한 로컬 게시 환경을 사용했습니다.

상세 근거: [실행 중인 MCP 갱신](verification/evidence/sh24604-release-1.26.0-live-update.json), [배포 검증 요약](verification/evidence/sh24604-release-1.26.0-summary.json).
