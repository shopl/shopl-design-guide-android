# Android SDG MCP

비 Android 개발자의 에이전트가 실제 SDG API와 기존 UI를 재사용하도록 돕는 로컬 MCP입니다. **최초 연결 이후 도구 호출 전에 최신 SDG 정보를 확인하고 자동으로 내려받습니다.** 최신 버전 하나만 제공하며, 일반 SDG 데이터 갱신에 재설치·재시작·업데이트 설정은 필요 없습니다.

설치는 [플러그인 안내](../plugins/sdg-android/README.md)를 따릅니다. Node.js 22 이상이 필요하며 초기 포함 데이터는 SDG 1.26.0입니다. 공식 데이터 게시 경로는 병합 후 최초 게시와 운영 검증이 필요합니다. [검증 결과와 한계](verification/REPORT.md)를 참고하세요.

## 사용 순서

1. 소비 저장소 지침과 대상 모듈·variant를 확인합니다. SDG component·template는 MCP에서, 앱의 kit은 `ui/sdg_kit`과 호출부에서 찾습니다. 용도에 맞는 kit·template·component와 기존 공용 UI를 우선 재사용합니다.
2. `sdg_resolve_context`를 호출합니다. 소비 앱의 SDG·Common·Resource 버전과 근거를 전달하면 최신 버전과의 차이를 안내합니다.
3. 컴포넌트·관련 타입·예제를 조회합니다. `catalogId`는 선택 사항이며 버전 선택이 아닌 동일 스냅샷 확인 용도입니다.
4. 앱의 SDG 버전이 낮으면 중앙 버전 선언을 최신 배포로 맞춥니다. 실제 Gradle 의존성 해석과 대상 모듈 컴파일을 확인하고 앱의 상태·이벤트·문자열 정책을 따릅니다.
5. 소비 저장소의 변경 레이어 리뷰를 수행합니다.

| 도구 | 용도 |
| --- | --- |
| `sdg_resolve_context` | 최신 버전·설정·소비 앱 의존성 차이 |
| `sdg_search_components` | 한글 목적·Kotlin 이름으로 component·template 검색 |
| `sdg_get_component` | 오버로드·receiver·인자·관련 타입·의존성·opt-in |
| `sdg_get_example` | 원본·조합 예제와 검증 범위 |
| `sdg_search_assets` | 색상·typography·간격·radius·drawable |
| `sdg_get_recipe` | 입력·선택·팝업·탭·목록 상태 조합 안내 |

소비 앱 비교 입력 예시:

```json
{"module":":features:user","variant":"devDebug","dependencyEvidence":[{"group":"com.shoplworks","artifact":"SDG-Android","version":"1.23.2","kind":"declared","source":"gradle/libs.versions.toml:128"}]}
```

선언만 확인했으면 `declared`, 실제 Gradle 해석을 확인했으면 `resolved`를 사용합니다. 소스 근거에는 전체 `sourceSha`와 변경 중인 경우 `dirty: true`가 필요합니다. MCP는 앱 파일이나 Gradle을 실행해 입력 근거를 검증하지 않습니다. 이전 버전에는 `version_mismatch`, 이전 `catalogId`에는 `catalog_unavailable`을 반환합니다. 이전 버전 호환성을 보장하지 않습니다. 검색은 기본 5개·최대 20개이며 cursor는 동일 스냅샷·검색 조건에서만 유효합니다.

## 자동 갱신

```text
공식 Remote 배포의 Sonatype PUBLISHED + 실제 배포 SHA·버전
 → Maven 세 모듈 소스·리소스 대조
 → 원본 샘플·별도 소비 코드 컴파일·MCP 진단
 → GitHub sdg-mcp-data 브랜치에 JSON 게시
 → 사용자 MCP의 다음 도구 호출에서 확인·적용
```

기본 주소는 `https://raw.githubusercontent.com/shopl/shopl-design-guide-android/sdg-mcp-data/latest.json`입니다. 별도 상시 서버나 사용자 GitHub 인증 없이 공개 JSON을 읽습니다. 실행 코드를 자동 다운로드하거나 실행하지 않습니다. 데이터 형식이나 추출기가 지원하지 않는 새 소스 구조는 MCP 유지보수가 필요합니다. 프로그램·스킬 자체 변경은 플러그인 업데이트 대상입니다.

- 배포자를 구분하지 않습니다. 공식 배포 경로 밖에서 Maven에 수동 게시한 경우에는 동기화 workflow에 실제 버전·SHA를 전달해 재검증해야 합니다.
- 검증 job은 `contents: read`, 게시 job만 `contents: write`입니다. 데이터 게시에는 PR·태그·npm 배포가 필요 없습니다. GitHub 조직 정책의 데이터 브랜치 쓰기 허용이 필요합니다.
- SHA-256·크기·형식·배포 식별자·검증 근거를 대조한 후 새 데이터를 적용합니다. 요청 중에는 같은 스냅샷을 사용하며 동시 갱신 요청은 합칩니다.
- 게시 실패·네트워크 장애·잘못된 데이터에는 마지막 검증본을 유지합니다. 낮은 버전과 같은 버전의 다른 배포 SHA를 거부하며 강제 push를 하지 않습니다.
- 해시 파일과 `latest.json`을 한 commit으로 게시합니다. 진행 중인 다운로드를 위해 원격의 과거 해시 파일은 보존하지만 MCP에서 과거 버전을 선택할 수는 없습니다.
- 로컬 캐시는 `~/.cache/sdg-android-mcp/<게시주소 해시>/`입니다. 여러 세션에서도 원자적으로 저장하며 손상된 캐시는 건너뜁니다.

| `update.state` | 의미 |
| --- | --- |
| `current` | 게시 경로의 최신 검증 데이터와 일치 |
| `offline` | 다운로드·검증 실패, 마지막 검증본 또는 초기 포함본 사용 |
| `remote_behind` | 원격의 이전 정보 거부, 기존 정보 유지 |
| `client_update_required` | 새 형식을 위한 MCP 프로그램 업데이트 필요 |
| `disabled` | 명시적 고정 설정 또는 배포 진단 |

`current`는 아직 검증·게시 중인 Maven 배포까지 반영했다는 뜻은 아닙니다. GitHub 게시·전파 시간이 필요합니다. 모든 도구 응답에서 갱신 상태와 경고를 확인할 수 있습니다. HTTP 요청 제한은 4초, manifest 64 KiB, 데이터 16 MiB입니다.

진단에만 `SDG_CATALOG_URL`(HTTPS, 로컬 진단은 loopback HTTP 허용), `SDG_CATALOG_CACHE_DIR`, `SDG_CATALOG_UPDATES=off`, `SDG_CATALOG_REGISTRY`를 사용합니다. 고정 registry는 원격 갱신을 끕니다. 일반 사용자는 설정할 필요가 없습니다.

## 개발·검증·게시

```sh
npm ci --prefix mcp
npm run build --prefix mcp
node mcp/dist/index.js
# JDK 21, Android SDK 36 필요
npm run verify --prefix mcp
npm run plugin:pack --prefix mcp
npm run feed:prepare --prefix mcp
```

- `verify`: 현재 최신 배포의 추출·Maven 대조·원본 샘플 및 소비 코드 컴파일·두 MCP 프로토콜 진단을 수행합니다.
- `sync -- --version X.Y.Z --source-sha FULL_SHA`: 지정한 실제 배포를 같은 검증 후 최신으로 반영합니다.
- `doctor`: 현재 서버의 연결·도구 조회를 진단합니다. Kotlin 컴파일은 다시 하지 않습니다.
- `plugin:pack`: 검증된 데이터를 포함해 서버·매니페스트·해시를 생성하고 독립 설치 진단 후 배포 파일을 만듭니다. 데이터 재검증을 대신하지 않습니다.
- `feed:prepare`: 기존 설치본도 읽는 공개 JSON 형식으로 내보냅니다.
- `feed:publish`: **실제 origin의 `sdg-mcp-data`에 게시하는 운영 명령**입니다. 격리 검증에서는 `-- --repository LOCAL_REPOSITORY`를 지정합니다.

검증 중단으로 `mcp/.cache/sync.lock`이 남으면 실행 중인 동기화가 없는지 확인한 후 정리합니다. MCP 프로그램 릴리스에서는 `package.json` 버전과 변경 기록을 함께 관리하고 플러그인을 다시 생성합니다.

## 저장 구조와 검증 범위

- `plugins/sdg-android/server/data/`: 최신 registry와 생성 카탈로그의 **단일 원본**입니다. 공통 소스·import·기본값은 한 번 저장하고 로더가 기존 조회 형식으로 복원합니다. API·타입·예제 내용을 잘라내지 않습니다.
- `mcp/dist/data/`: 빌드 시 생성하는 무시된 복사본입니다. 저장소에는 커밋하지 않습니다.
- `mcp/.cache/runs/`: 실행별 원본 카탈로그·변경 비교·진단·로그입니다. GitHub Actions artifact로 보관하며 저장소와 설치본에는 누적하지 않습니다.
- `plugins/sdg-android/server/index.mjs`, `release.json`: 최초 설치용 번들과 파일 해시입니다. 소스 변경 후 다시 생성해 함께 커밋합니다. CI가 재생성 일치를 검사합니다.
- `mcp/verification/`: 외부 소비 컴파일 프로젝트·조합 예제 6개와 현재 검증 요약입니다.

카탈로그 내용이 바뀌면 `catalogId`가 바뀝니다. 검증 시각·로그 해시만 바뀌면 ID는 유지되고 `verificationId`가 갱신됩니다. 검색 안내·예제를 바꾼 경우에도 `verify`로 다시 생성해야 합니다.

SDG 1.26.0에는 API 오버로드 194개, 타입 370개, 에셋 301개, 원본 예제 245개, 조합 예제 6개가 포함됩니다. 원본 예제는 원래 모듈 문맥에서, 조합 예제는 지정 소비 환경의 전체 파일로 컴파일합니다. 타입·예제 연결은 import·package·인자 기반 후보이며 완전한 Kotlin 의미 분석은 아닙니다. 컴파일 성공은 화면 동작·접근성·모든 Kotlin/Compose 버전 호환성을 보장하지 않습니다.
