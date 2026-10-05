# SDG 버전 PR 버튼

대상은 shopl 워크스페이스(`T016H73FHDH`)의
`#sdg-android-deploy`(`C093TU3CCBG`)입니다. Slack에서 실행하는 앱이 GitHub
Actions를 요청하므로 별도 서버를 운영하지 않습니다.

버튼을 누르면 이전 릴리스부터 main까지 병합된 PR을 조회합니다. `add` 라벨이
하나라도 있으면 minor, 없으면 patch를 올립니다. VERSION 변경, 버전 PR 생성, 버전
라벨 및 Android 팀 리뷰 요청까지 수행합니다. 열려 있는 버전 PR이 있으면 해당 PR
링크를 반환합니다. 결과는 기존 `SLACK_SDG_ANDROID_WEB_HOOK`으로 채널에 알립니다.

## 최초 설치

1. `.github/workflows/create-version-pr.yml`을 main에 병합합니다. GitHub
   저장소의 Actions 설정에서 **Allow GitHub Actions to create and approve pull
   requests**가 허용되어 있어야 합니다. 기존 배포 알림 웹훅이 `C093TU3CCBG`를
   가리키는지 확인합니다.
2. [Slack CLI](https://docs.slack.dev/tools/slack-cli/guides/installing-the-slack-cli-for-mac-and-linux/)와
   Deno를 설치하고 `slack login`으로 shopl 워크스페이스를 인증합니다. Slack에서
   호스팅하는 앱은 유료 플랜과 워크스페이스의 앱 설치 승인이 필요합니다.
3. 이 디렉터리에서 `slack deploy --team T016H73FHDH`를 실행합니다. 앱은 워크플로
   실행(`commands`)과 메시지 게시(`chat:write`) 권한을 요청합니다. 채널 내용
   조회 권한은 요청하지 않습니다.
4. GitHub의 봇 또는 서비스 계정으로 **shopl/shopl-design-guide-android
   저장소만** 선택한 fine-grained token을 발급합니다. 권한은 **Actions: Read and
   write**만 필요합니다. 토큰을 git에 커밋하거나 Slack 메시지로 보내지 않고
   배포된 앱의 `SDG_GITHUB_ACTIONS_TOKEN` 환경 변수에 설정합니다.
   `slack env set SDG_GITHUB_ACTIONS_TOKEN --app <배포된 앱 ID>`로 숨김 입력창에
   직접 입력합니다. 토큰 입력은 운영 담당자가 로컬에서 수행합니다.
5. `#sdg-android-deploy` 채널에 **SDG Version PR** 앱을 초대합니다.
6. `slack trigger create --trigger-def triggers/create-version-pr.ts --app <배포된 앱 ID>`로
   운영용 링크를 생성합니다.
   `slack trigger access --trigger-id <트리거 ID> --grant --channels C093TU3CCBG --app <배포된 앱 ID>`로
   해당 채널 멤버에게 실행 권한을 설정하고 `--info`로 확인합니다.
7. 생성된 Shortcut URL을 채널에 게시하면 실행 버튼이 표시됩니다. 메시지를
   고정하거나 채널 링크에 등록합니다. `slack run`으로 만든 개발용 링크는
   운영용으로 사용하지 않습니다.

## 확인

```sh
deno task check
node ../../.github/scripts/version-pr-summary.test.cjs
node ../../.github/scripts/create-version-pr.test.cjs
```

GitHub에서 워크플로를 수동 실행해 PR 생성 및 결과 알림을 확인한 뒤, 채널 버튼을
눌러 같은 PR 링크가 반환되는지 확인합니다. 이 앱은 PR 생성까지만 수행하며,
리뷰·병합·배포는 기존 절차를 따릅니다.
