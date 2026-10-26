# 원격 배포 동기화 보완 결과

> 과거 구현 기록입니다. 이후 사용자 요청으로 최신 배포 카탈로그 하나만 제공하도록 변경했습니다. 현재 정책과 검증은 [LATEST_ONLY_REPORT.md](LATEST_ONLY_REPORT.md)를 참고하세요.

검증일: 2026-10-26. 원격 workflow를 실제 배포로 실행한 결과가 아니라 로컬 구현·컴파일·연결 검증 결과입니다.

## 명시된 요구사항 기반 변경

사용자의 “SDG Remote 배포될 때를 동기화 시점으로” 지시에 따라 기존 Maven 배포 workflow의 Sonatype `PUBLISHED` 출력에 카탈로그 동기화를 연결했습니다. 업로드 접수·태그·PR 병합만으로 동기화하지 않습니다. 배포 job이 실제로 사용한 commit SHA와 버전을 후속 작업으로 전달합니다.

## 추론·해석으로 구현한 부분

- 검증 성공 후 카탈로그 변경 PR을 생성·갱신하고 담당자 리뷰 후 병합하는 방식을 선택했습니다. 자동 병합이나 npm 게시를 하지 않습니다. `.github/workflows/mcp-sync-release.yml`에 구현했습니다.
- 기존 소비 앱을 지원하기 위해 1.23.2와 1.25.2 카탈로그를 함께 유지합니다. `data/registry.json`과 요청별 카탈로그 문맥으로 버전 선택을 분리했습니다.
- 새 실행 폴더에서 후보를 생성하고 모든 검증 후 registry를 원자적으로 갱신합니다. 이전 버전의 삭제된 소스가 검증 폴더에 남는 문제를 피하기 위한 선택입니다.
- 카탈로그 내용 ID와 검증 revision을 분리했습니다. 검증 시각·로그 변경은 카탈로그 ID를 바꾸지 않습니다. 이 전환으로 초기 단일 버전 카탈로그 ID는 새 ID로 변경됐습니다. 클라이언트는 context를 다시 조회해야 합니다.
- 검색 별칭·조합 안내를 카탈로그별로 보관하고, 실행 중인 새 서버는 registry 교체를 다음 요청에 반영합니다. MCP 실행 코드 자체가 바뀐 경우에는 재연결이 필요합니다.
- 공개 Maven 전파 지연 재시도, 동일 버전의 SHA 변경 거부, 이미 검증한 배포 아티팩트 변경 거부, 연결 진단 명령과 버전별 API 변경 보고서를 추가했습니다.

## 검증 결과

| SDG | API 오버로드 | 에셋 | 원본 예제 | 조합 예제 | 소스 대조·컴파일·연결 |
| --- | ---: | ---: | ---: | ---: | --- |
| 1.23.2 | 187 | 290 | 232 | 6 | 통과 |
| 1.25.2 | 192 | 301 | 241 | 6 | 통과 |

실행한 주요 명령:

```sh
ANDROID_HOME=/Users/shopl/Library/Android/sdk npm run verify --prefix mcp
npm run build --prefix mcp
npm run doctor --prefix mcp
actionlint .github/workflows/mcp-sync-release.yml .github/workflows/mcp-check.yml
actionlint -shellcheck= .github/workflows/mcp-sync-release.yml .github/workflows/mcp-check.yml .github/workflows/auto-deploy.yml
git diff --check
```

모두 종료 코드 0입니다. 원본 샘플은 해당 release SHA의 wrapper로, 소비 코드는 배포 SDG 의존성을 지정하여 컴파일했습니다. 컴파일과 연결 기록은 `data/<version>-<content-id>.json`, `data/<version>-protocol.json`에 있습니다. 로컬 전체 로그는 `/tmp/sdg-sync-all.log`, `/tmp/sdg-doctor-final.log`, `mcp/.cache/runs/`에 있습니다.

추가로 확인한 경계:

- 기존 1.25.2에 다른 SHA를 지정하면 거부하고 registry를 유지합니다.
- 소스의 배포 버전과 요청 버전이 다르면 거부하고 registry를 유지합니다.
- 검증 시각과 로그 해시만 바꿔도 카탈로그 ID는 유지됩니다.
- 같은 서버 프로세스에서 지원 목록을 1개에서 2개로 갱신합니다.
- 두 버전을 섞어 동시에 20회 조회했을 때 `Spacing36`은 1.23.2에 없고 1.25.2에만 있습니다.
- 존재하지 않는 카탈로그는 최신 버전으로 대체하지 않습니다.
- Codex App Server에서도 `connected` 상태와 두 버전의 context·에셋 조회를 확인했습니다.

[경계 확인 기록](evidence/update-boundaries.json), [Codex 연결 확인](evidence/codex-updated-catalogs.json)을 참고하세요. 영구 테스트 파일은 추가하지 않았습니다. `doctor`는 실제 서버 연결을 확인하는 운영 진단 명령입니다.

Shopl 레이어 리뷰 하네스 대상은 없습니다. 변경은 MCP와 workflow에 한정되며 `data/**`, `domain/**`, `features/**`, `ui/**`는 수정하지 않았습니다. Android 문자열 리소스와 Kotlin UI 예제도 이번 보완에서 수정하지 않았습니다.

## 아직 활성화하지 않은 부분

workflow와 MCP 구현은 로컬 작업입니다. 원격 저장소에 반영·병합하지 않았고 실제 배포·npm 게시·GitHub 동기화 PR 생성도 실행하지 않았습니다. 원격 적용 시 Actions의 PR 생성 허용 설정이 필요합니다. 기존 SDG 배포 job의 비밀값 설정은 그대로 사용하며 동기화 job에는 별도 배포 비밀값을 전달하지 않습니다.

GITHUB_TOKEN이 생성한 PR의 일반 PR workflow는 자동 실행되지 않을 수 있습니다. 동기화 job이 수행한 검증 로그를 확인하고 필요하면 MCP 검증 workflow를 해당 브랜치에서 수동 실행합니다. 기존 원격 validator의 브랜치·리뷰 정책도 적용 시 확인해야 합니다.

화면 런타임·접근성·비 Android 개발자 파일럿, 에이전트의 자발적 MCP 호출 평가, 팀 배포 경로와 클라이언트 업데이트·롤백 운영은 남아 있습니다.
