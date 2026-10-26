# 최신 배포 버전 전용 전환

> 최신 전용 전환 시점의 기록입니다. 이후 component·template·kit 재사용 지침을 보완하고 자동 PR 생성을 제거했습니다. 후속 검증과 현재 운영 범위는 [README](../README.md)의 본 레포 재사용 지침 검증을 참고하세요.

검증일: 2026-10-26. 로컬 구현·검증 결과입니다. 원격 workflow는 아직 활성화하지 않았습니다.

## 명시된 요구사항 기반 변경

사용자의 “항상 최신 버전만 제공하면 돼” 요청에 따라 최신 배포 카탈로그 하나만 제공합니다.

- `src/catalog.ts`: registry에 정확히 한 개만 허용합니다. 소비 앱의 버전으로 카탈로그를 선택하지 않습니다.
- `src/tools.ts`, `src/query.ts`, `src/index.ts`: 입력 없이 최신 context를 조회합니다. 다른 조회 도구의 `catalogId`도 생략할 수 있습니다.
- `data/registry.json`: 현재 최신 배포 1.25.2만 제공합니다. 1.23.2 카탈로그와 해당 연결 진단 payload를 제거했습니다. 과거 변경 보고서는 이력으로만 남습니다.
- `scripts/sync-release.mjs`, `scripts/copy-data.mjs`: 새 배포 검증 후 한 개의 카탈로그로 교체합니다. 이전 payload는 source와 dist에서 제거합니다.
- `package.json`, README, 동기화 workflow 안내를 최신 전용 정책으로 변경했습니다. `--current`는 현재 카탈로그를 재검증하며 과거 버전을 추가하지 않습니다.

## 추론·해석으로 구현한 부분

- 소비 앱이 이전 버전이어도 최신 API를 조회할 수 있게 했습니다. `sdg_resolve_context`는 `version_mismatch`와 최신 카탈로그를 반환합니다. 이전 버전에서도 API가 동작한다는 오해를 막기 위해 의존성 업데이트·컴파일 확인을 안내합니다. `src/tools.ts`에 구현했습니다.
- 명시한 `catalogId`가 오래되면 context 재조회를 요청합니다. 같은 작업 중 서로 다른 스냅샷의 API가 섞이지 않게 하기 위한 판단입니다. ID는 버전 선택 기능이 아닙니다. `src/query.ts`에 구현했습니다.
- 늦게 도착한 과거 배포가 최신 정보를 덮어쓰지 않도록 현재보다 낮은 버전의 동기화를 거부합니다. `scripts/sync-release.mjs`에 구현했습니다.
- 검증 실패 시 현재 카탈로그를 유지하는 기존 방식을 유지했습니다. 최신 배포 반영은 검증·동기화 PR 병합·로컬 갱신 후 완료됩니다. 요청마다 원격 정보를 조회하는 방식은 아닙니다.

## 검증 결과

- Maven Central metadata에서 최신 배포 1.25.2 확인.
- `ANDROID_HOME=/Users/shopl/Library/Android/sdk npm run verify --prefix mcp`: 종료 코드 0. SDG 318개·Common 26개 소스의 배포 바이트 일치, drawable 심볼 161개 확인. 원본 샘플과 소비 API 192개·에셋 301개·조합 예제 6개의 컴파일 통과.
- 두 MCP 프로토콜에서 각각 도구 20회 호출 통과. 기본 최신 조회, 이전 버전 소비 앱 안내, 오래된 ID 거부, ID 없이 6개 도구 호출 확인. [연결 진단](../data/1.25.2-protocol.json).
- Codex App Server의 실제 `sdg-android` 연결은 `connected`. 최신 1.25.2만 반환하며 `Spacing36` 조회 성공. [Codex 확인 기록](evidence/codex-latest-only.json).
- 1.23.2로 되돌리는 동기화 요청 거부 및 registry 불변 확인. source·dist에 카탈로그가 한 개만 있고 이전 payload가 없는 것을 확인. [경계 확인 기록](evidence/latest-only.json).
- TypeScript 검사, 새 MCP workflow 두 개의 actionlint, 기존 배포 workflow를 포함한 shellcheck 제외 actionlint, `git diff --check`: 종료 코드 0.
- 전체 검증 로그: `/tmp/sdg-latest-only-verify.log`.

영구 테스트 파일과 기기 실행은 추가하지 않았습니다. 기존 운영 진단 명령을 최신 전용 정책에 맞게 수정했습니다. Shopl 레이어 하네스 대상은 없습니다. 이번 변경은 `mcp/**`와 workflow이며 `data/**`, `domain/**`, `features/**`, `ui/**`를 수정하지 않았습니다.

## 남은 작업

원격 저장소 반영, 동기화 workflow 활성화, 팀 배포와 클라이언트 자동 업데이트는 남아 있습니다. 이번 작업에서 PR 생성·배포·npm 게시·앱 의존성 변경은 실행하지 않았습니다.
