# Android SDG MCP 검증 결과

검증일: 2026-10-25 · 대상: SDG 1.23.2 · 카탈로그: `sdg-android:1.23.2:5d99c035a42d2a02`

로컬 빌드, 배포 소스 대조, Android 소비 환경 컴파일, MCP 통신 검증이 통과했습니다. 화면 실행과 실제 사용자 파일럿은 수행하지 않았습니다.

## 명시된 요구사항 기반 변경

사용자의 “검증 시작해 그럼” 요청에 따라 앞서 구현한 MCP를 검증했습니다. 배포는 나중에 진행한다는 지시에 따라 npm 게시, 원격 저장소 생성, 에이전트 설정 변경은 수행하지 않았습니다.

| 확인 대상 | 결과 | 근거와 범위 |
| --- | --- | --- |
| Kotlin PSI 추출기·TypeScript·카탈로그 | 통과 | `npm run build --prefix mcp`, 종료 코드 0 |
| 배포 소스 대응 | 통과 | SDG 301개 + Common 25개 Kotlin 파일의 목록·바이트가 고정 commit과 일치 |
| 배포 drawable 심볼 | 통과 | Resource AAR의 `R.txt`에서 카탈로그의 156개 drawable 이름 확인 |
| 원본 예제 232개 | 통과 | 원본 샘플 앱의 `:app:compileDebugKotlin`, 종료 코드 0 |
| 별도 소비 프로젝트 | 통과 | 배포 SDG를 사용해 API 187개·에셋 참조 290개·조합 예제 6개 컴파일, 종료 코드 0 |
| MCP stdio 통신·경계 입력 | 통과 | 2개 프로토콜에서 총 51개 확인 항목. `protocol.json` 참조 |
| 검증 근거의 유효성 | 통과 | 현재 입력 해시 일치. API 시그니처 변경 시 해시 불일치 확인 |
| 변경 파일 공백·패치 검사 | 통과 | `git diff --check`, 종료 코드 0 |
| Shopl 레이어 리뷰 하네스 | 대상 없음 | 변경은 `mcp/**`에 한정. `data/**`, `domain/**`, `features/**`, `ui/**` 변경 없음 |

컴파일 검증 환경은 JDK 21, AGP 8.10.0, Kotlin 2.1.20, compileSdk 36, minSdk 28, Compose BOM 2025.05.01입니다. 이 조합의 성공을 다른 모든 환경의 호환성으로 일반화하지 않습니다.

## 추론·해석으로 구현한 부분

검증 방법과 예제 구성은 사용자가 세부 지정하지 않았으므로 아래 항목은 구현 판단입니다.

- `verification/`에 외부 소비 프로젝트와 전체 파일 조합 예제 6개를 추가했습니다. 비 Android 개발자의 에이전트가 실제 호출 가능한 코드를 받는지 확인하기 위한 선택입니다. 문구·상태·이벤트는 호출 화면에서 전달합니다.
- `scripts/verify-artifacts.py`, `scripts/verify-builds.mjs`와 컴파일 입력 생성기를 추가했습니다. 같은 검증을 재실행하고 검증 범위를 기록하기 위한 선택입니다. 테스트 파일은 작성하지 않았습니다.
- `src/references.ts`에서 타입·예제 연결을 import와 package 기준으로 제한했습니다. 이름만 같은 구형 타입이 현재 컴포넌트에 연결되는 문제를 확인했기 때문입니다. 예제 연결은 인자 구성도 대조하지만 완전한 오버로드 해석은 아닙니다.
- 추출 시 API 시그니처에서 KDoc을 분리했습니다. 오래된 `@sample` 내용이 호출 시그니처에 섞이는 문제를 줄였습니다. 원본 KDoc은 documentation에 남깁니다.
- `src/tools.ts`에 Joda 명시 의존성과 원본 `@OptIn` 안내를 추가했습니다. 외부 소비 컴파일에서 해당 조건이 필요함을 확인했기 때문입니다. Joda POM의 runtime 의존성만으로 DateTime을 사용하는 Kotlin 코드가 컴파일되지는 않았습니다.
- 검증 상태를 원본 문맥 컴파일과 전체 파일 소비 컴파일로 구분했습니다. 코드·설정 변경 후 과거 성공을 현재 결과로 오인하지 않도록 입력 해시를 기록합니다.
- 빈 ID를 스키마 오류로 처리했습니다. 빈 assetId·recipeId가 전체 목록 요청으로 해석되는 혼동을 막기 위한 선택입니다.

## 실행과 증거

저장소 루트에서 실행한 전체 검증 명령:

```sh
ANDROID_HOME=/Users/shopl/Library/Android/sdk npm run verify --prefix mcp
```

종료 코드 0. 컴파일 명령:

```sh
./gradlew -p mcp/.cache/source-validation :app:compileDebugKotlin --console=plain --max-workers=4
./gradlew -p mcp/verification compileDebugKotlin --console=plain --max-workers=4
```

두 명령 모두 종료 코드 0. 이후 MCP 안내 문구·입력 스키마 변경에 대해 `npm run build --prefix mcp`도 종료 코드 0으로 완료했습니다.

- [배포 아티팩트 근거](evidence/artifacts.json): 공개 다운로드 URL, SHA-256, 소스 수와 심볼 수.
- [컴파일 근거](evidence/compilation.json): 명령, 종료 코드, 환경, 입력 해시와 로그 해시.
- [MCP 통신 확인 결과](evidence/protocol.json): `2025-11-25` 초기화, `2026-07-28` discovery, 6개 도구, 한글 검색, 타입 조회, 예제 상태, 6개 조합, 페이지 중복 여부, 잘못된 버전·SHA·cursor·ID·limit, 충돌 근거, Joda·opt-in 안내.

로컬 로그는 `mcp/.cache/verification.log`, `source-compile.log`, `consumer-compile.log`, `final-build.log`에 있습니다. 캐시 로그는 Git 추적 대상이 아니며 재실행 시 바뀝니다. `npm run verify`는 컴파일 근거를 갱신하지만 MCP 통신 확인을 자동으로 반복하지 않습니다.

첫 소비 컴파일에서 AndroidX 설정, Joda 의존성, Material opt-in 누락을 확인한 뒤 수정했습니다. 이 실패를 SDG 라이브러리 자체 결함으로 판단하지 않았습니다. 원본의 deprecated API 안내는 이번 변경 범위에서 수정하지 않았습니다.

## 남은 검증

- 실제 Shopl 앱의 대상 모듈·variant 의존성 해석과 적용 컴파일.
- 실제 에이전트 클라이언트 설정에서 연결. 이번 검증은 로컬 stdio 클라이언트입니다.
- 화면 실행·터치·접근성·기기별 동작. 컴파일 입력은 실행하지 않았습니다.
- 비 Android 개발자 파일럿과 기존 방식 대비 작업 시간·오류 비교.
- Android 담당자의 조합 가이드 검토.
- Kotlin 의미 분석에 의한 정확한 오버로드 연결과 AAR 바이너리 재현 빌드 비교는 구현 범위 밖입니다.

Android 문자열 리소스는 수정하지 않았습니다. 기존 메인 체크아웃의 별도 작업도 수정하지 않았습니다.
