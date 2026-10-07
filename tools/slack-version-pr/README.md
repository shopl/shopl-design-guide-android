# SDG 버전 PR 버튼과 채팅창 실행

대상은 shopl 워크스페이스(`T016H73FHDH`)의
`#sdg-android-deploy`(`C093TU3CCBG`)입니다. Slack에서 실행하는 앱이 GitHub
Actions를 요청하므로 별도 서버를 운영하지 않습니다.

## 팀원 사용 방법

- 고정된 안내 메시지의 **버전 PR 생성** 버튼을 누릅니다.
- 안내 메시지를 찾기 어려우면 채널의 채팅창에 `/SDG`를 입력하고 목록에서 **SDG
  버전 PR 만들기**를 선택합니다. 두 진입점은 같은 생성 워크플로를 실행합니다.
- **SDG 버전 PR 안내**를 선택하면 실행한 사람에게만 버튼과 사용법을 보여줍니다.
  이 안내만 실행해서는 GitHub 요청이나 채널 공개 게시·고정이 일어나지 않습니다.

채팅창 실행은 Slack의
[워크플로 바로가기](https://docs.slack.dev/faq/#how-do-i-build-a-slash-command-in-apps-created-with-the-deno-slack-sdk)를
사용합니다. `/sdg-version create`처럼 문자열을 입력하고 바로 전송하는 사용자
정의 slash command는 등록하지 않습니다. 팀원은 해당 트리거의 실행 권한만 있으면
되며 개인 GitHub 토큰이나 CLI 설치가 필요 없습니다.

버튼을 누르면 이전 릴리스부터 main까지 병합된 PR을 조회합니다. `add` 라벨이
하나라도 있으면 minor, 없으면 patch를 올립니다. VERSION 변경, 버전 PR 생성, 버전
라벨 및 Android 팀 리뷰 요청까지 수행합니다. 열려 있는 버전 PR이 있으면 해당 PR
링크를 반환합니다. 결과는 기존 `SLACK_SDG_ANDROID_WEB_HOOK`으로 채널에 알립니다.

## 설치된 운영 앱 (2026-10-06)

[SDG Version PR](https://slack.com/apps/A0C7SN83G8G) (`A0C7SN83G8G`)의 Slack
서버 검증·배포·채널 연결을 완료했습니다. 운영 트리거는 `Ft0C6H0H2CUF`이며, 실행
권한은 `C093TU3CCBG` 채널 멤버에게만 설정했습니다.
[운영 실행 링크](https://slack.com/shortcuts/Ft0C6H0H2CUF/5fc960098cfa9655c5c795b3accbe3ac)는
main 병합과 GitHub 토큰 설정 후 채널에 게시합니다. 현재 운영 실행 검증은 대기
중입니다.

## 안내 버튼 준비 및 직접 테스트 (2026-10-08)

안내는
[workflow button](https://docs.slack.dev/reference/block-kit/block-elements/workflow-button-element/)으로
같은 버전 PR 생성 트리거를 실행합니다. 기본 동작은 `chat.postEphemeral`로 실행한
사람에게만 표시하는 것입니다. 공개 게시·고정은 별도 트리거를 사람이 직접 실행할
때만 동작하며 앱 배포나 GitHub CI에서 자동으로 실행하지 않습니다.

1. 이 디렉터리에서 변경된 앱을 배포합니다. `pins:write` 권한은 안내 메시지
   고정에 사용하며 채널 내용 조회 권한은 추가하지 않습니다. 앱 배포 자체는
   메시지를 게시하거나 main을 병합하지 않습니다.
2. 앱의 `SDG_VERSION_PR_TRIGGER_URL`에 같은 앱·환경의 **SDG 버전 PR 만들기**
   Shortcut URL을 설정합니다. 운영 앱은 위 운영 실행 링크를 사용합니다. 테스트용
   앱에는 운영 PR을 생성하지 않는 테스트 트리거를 연결하고 해당 앱의
   `SDG_GITHUB_ACTIONS_TOKEN`을 설정하지 않습니다.
3. `slack trigger create --trigger-def triggers/version-pr-guide.ts --app <앱 ID>`로
   개인 안내 트리거를 생성합니다. 직접 테스트 중에는 실행 권한을 테스트 담당자
   한 명으로 제한하고 링크를 공유 채널에 게시하지 않습니다.
4. 직접 테스트할 때 링크 또는 채널의 `/SDG` 검색에서 **SDG 버전 PR 안내**를
   선택합니다. 안내가 본인에게만 보이고 채널 메시지·핀은 추가되지 않는지
   확인합니다. 생성 버튼의 실제 PR 생성 경로는 main의 워크플로 설치와 운영 토큰
   설정이 필요합니다. 본 PR은 팀의 검토와 직접 테스트를 거친 뒤 팀에서
   병합합니다.
5. 운영 게시가 필요해졌을 때만
   `slack trigger create --trigger-def triggers/publish-version-pr-guide.ts --app <앱 ID>`로
   **SDG 버전 PR 안내 게시·고정** 트리거를 생성합니다. 실행 권한은 운영
   담당자에게만 주고 해당 담당자가 한 번 실행합니다. 게시 후 고정이 실패하면
   오류 안내에 따라 이미 게시된 메시지를 수동으로 고정합니다. 재실행하면 안내가
   다시 게시됩니다.

2026-10-08 변경은 공개 채널 게시·고정 및 실제 버튼 실행을 수행하지 않고 요청을
대체한 로컬 검사로 검증합니다. 직접 Slack 테스트는 담당자가 진행합니다.

안내 기능의 Slack 배포와 개인 안내 트리거(`Ft0C7LBKJUJW`) 생성을 완료했습니다.
현재 해당 트리거의 실행 권한은 Scott(`U07SM8410PP`) 한 명에게만 있습니다. 공개
게시·고정 트리거는 생성하지 않았습니다. Deno 타입·lint·대체 요청 검사, Node 버전
계산 검사 및 Slack 매니페스트 검증이 통과했습니다. 실제 안내 표시와 버튼
클릭·게시·고정은 담당자가 직접 확인합니다.

## 병합 전 테스트 (2026-10-07)

PR의 `validate` 작업은 쓰기 권한 없이 버전 계산·중복 실행·오류 처리 검사를
실행하고, 실제 main의 버전 변경을 `dryRun: true`로 확인합니다. PR 실행에서는
운영 `create` 작업과 Slack 배포 웹훅 알림을 실행하지 않습니다.

로컬에서 같은 읽기 전용 조회를 실행할 수 있습니다. GitHub CLI(`gh`) 인증이
필요하며, GitHub API는 이 저장소의 GET 요청만 허용합니다.

```sh
node preview.cjs
```

별도 **SDG Version PR Test (local)** 앱(`A0C6WBJ3YR5`)의
[테스트 버튼 게시물](https://shopl-workspace.slack.com/archives/C093TU3CCBG/p1791357022469899)에서
미리보기를 실행할 수 있습니다. 실행 권한은 `C093TU3CCBG` 채널 멤버로
제한했습니다. 개발 앱은 이 Mac에서 `slack run`이 실행 중일 때만 동작합니다. 로컬
테스트 앱의 소스·설치 정보는 Git에서 제외된 `.slack/preview-app`에 보관합니다.

[실제 Slack 실행 결과](https://shopl-workspace.slack.com/archives/C093TU3CCBG/p1791357253801889)에서
`v1.22.0 → v1.23.0`의 `add` 3개 → minor, `v1.23.0 → v1.23.1`의 `add` 0개 →
patch, 현재 main의 변경사항 없음 결과를 확인했습니다. 브랜치·PR·라벨은 변경하지
않습니다. 운영 GitHub 토큰과 실제 PR 생성·리뷰 요청·배포 웹훅 경로는 이 미리보기
검증에 포함되지 않으며 운영 실행 전에 별도 확인해야 합니다.

## 최초 설치

1. `.github/workflows/create-version-pr.yml`을 main에 병합합니다. GitHub
   저장소의 Actions 설정에서 **Allow GitHub Actions to create and approve pull
   requests**가 허용되어 있어야 합니다. 기존 배포 알림 웹훅이 `C093TU3CCBG`를
   가리키는지 확인합니다.
2. [Slack CLI](https://docs.slack.dev/tools/slack-cli/guides/installing-the-slack-cli-for-mac-and-linux/)와
   Deno를 설치하고 `slack login`으로 shopl 워크스페이스를 인증합니다. Slack에서
   호스팅하는 앱은 유료 플랜과 워크스페이스의 앱 설치 승인이 필요합니다.
3. 이 디렉터리에서 `slack deploy --team T016H73FHDH`를 실행합니다. 앱은 워크플로
   실행(`commands`), 메시지 게시(`chat:write`), 고정(`pins:write`) 권한을
   요청합니다. 채널 내용 조회 권한은 요청하지 않습니다.
4. GitHub의 봇 또는 서비스 계정으로
   [토큰 발급 화면](https://github.com/settings/personal-access-tokens/new?name=SDG+Version+PR&target_name=shopl&expires_in=90&actions=write)을
   열고 **shopl/shopl-design-guide-android 저장소만** 선택한 fine-grained
   token을 발급합니다. 권한은 **Actions: Read and write**만 필요합니다. 토큰을
   git에 커밋하거나 Slack 메시지로 보내지 않고 배포된 앱의
   `SDG_GITHUB_ACTIONS_TOKEN` 환경 변수에 설정합니다.
   `slack env set SDG_GITHUB_ACTIONS_TOKEN --app <배포된 앱 ID>`로 숨김 입력창에
   직접 입력합니다. 토큰 입력은 운영 담당자가 로컬에서 수행합니다.
5. `#sdg-android-deploy` 채널에 **SDG Version PR** 앱을 초대합니다.
6. `slack trigger create --trigger-def triggers/create-version-pr.ts --app <배포된 앱 ID>`로
   운영용 링크를 생성합니다.
   `slack trigger access --trigger-id <트리거 ID> --grant --channels C093TU3CCBG --app <배포된 앱 ID>`로
   협업자 포함 질문에는 **No**를 선택하고, `--info`로 해당 채널 멤버만 실행할 수
   있는지 확인합니다.
7. `SDG_VERSION_PR_TRIGGER_URL` 설정과 개인 안내 테스트를 진행한 뒤 위의 **안내
   게시·고정** 절차로 버튼을 게시합니다. 개인 안내 트리거는 운영 시 채널
   멤버에게 실행 권한을 줍니다. 게시·고정 트리거는 운영 담당자에게만 줍니다.
   `slack run`으로 만든 개발용 링크는 운영용으로 사용하지 않습니다.

## 확인

```sh
deno task check
node ../../.github/scripts/version-pr-summary.test.cjs
node ../../.github/scripts/create-version-pr.test.cjs
```

GitHub에서 워크플로를 수동 실행해 PR 생성 및 결과 알림을 확인한 뒤, 채널 버튼을
눌러 같은 PR 링크가 반환되는지 확인합니다. 이 앱은 PR 생성까지만 수행하며,
리뷰·병합·배포는 기존 절차를 따릅니다.
