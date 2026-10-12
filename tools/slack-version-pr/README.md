# SDG 버전 PR 버튼과 채팅창 실행

운영 대상은 shopl 워크스페이스(`T016H73FHDH`)의
`#sdg-android-deploy`(`C093TU3CCBG`)입니다. Slack 앱이 GitHub Actions를
요청하므로 별도 서버를 운영하지 않습니다.

## 팀원 사용 방법

1. 채널 상단 **워크플로** 탭에서 **SDG 버전 PR 만들기**를 실행합니다.
   채팅창에 `/SDG`를 입력해 같은 워크플로를 선택할 수도 있습니다.
2. 본인에게만 뜨는 입력창의 **개인 토큰 생성** 링크를 엽니다.
   [fine-grained token 발급 화면](https://github.com/settings/personal-access-tokens/new?name=SDG+Version+PR&target_name=shopl&expires_in=30&actions=write)에서
   Resource owner는 **shopl**, Repository access는 **Only select repositories →
   shopl-design-guide-android**, 권한은 **Actions: Read and write**로 설정합니다.
   링크는 이름·대상 조직·권한·30일 만료를 미리 채우며 저장소는 직접 선택합니다.
   조직이 토큰 승인을 요구하면 승인 후 사용합니다.
3. 발급한 개인 토큰을 입력하고 **PR 생성 요청**을 누릅니다.
   토큰은 실행할 때마다 입력합니다. 앱은 토큰을 저장하지 않으므로 재사용할 토큰은
   본인이 안전하게 보관합니다. 토큰 만료·삭제 후에는 새 토큰을 사용합니다.
4. 채널의 접수 안내와 PR 링크를 확인합니다. PR 리뷰·병합·배포는 팀에서 진행합니다.

[GitHub 공식 문서](https://docs.github.com/en/rest/actions/workflows#create-a-workflow-dispatch-event)에
따르면 실행 요청에는 Actions 쓰기 권한이 필요합니다. 개인 토큰은 계정 확인과
워크플로 실행 요청에만 사용합니다. `GET /user`로 확인한 토큰 소유자의 고유 ID를
담당자로 전달하므로 GitHub 이름이 바뀌어도 같은 계정을 지정합니다.

토큰 원문은 채널 메시지, 함수 입출력, 모달 메타데이터, 로그, 앱 환경 변수 또는
데이터스토어에 넣지 않습니다. 계정 확인·실행 요청이 실패하면 입력창에 오류를
표시하고, 취소하면 GitHub 요청이나 채널 메시지를 보내지 않습니다. 실행 요청을
접수한 뒤의 Slack 알림은 별도 단계에서 처리합니다. 통신 오류로 접수 여부가 불명확하면
GitHub Actions 실행 결과를 먼저 확인합니다.

`SDG_GITHUB_ACTIONS_TOKEN`, `SDG_GITHUB_USER_MAP`, OAuth 앱 등록은 이 구현에
필요하지 않습니다. 기존 운영 앱에 남아 있는 설정은 새 코드에서 사용하지 않습니다.

## 생성되는 PR

최신 릴리스부터 main까지의 변경과 연결된 병합 PR을 조회합니다. `add` 라벨이
하나라도 있으면 minor, 없으면 patch를 올립니다. 변경사항이 없으면 PR을 만들지
않으며 열려 있는 버전 PR이 있으면 해당 PR을 안내합니다.

실제 코드 수정은 GitHub Actions의 `GITHUB_TOKEN`으로 수행합니다. main에서
새 버전 브랜치를 만들고 `PublishingConfig.VERSION`만 수정한 커밋으로 PR을
생성합니다. PR 본문에는 이전 릴리스·권장 버전·판정 근거·PR 번호와 라벨·전체
비교 링크를 표시합니다. PR 제목은 링크에서 보이므로 목록에 중복 작성하지 않습니다.

요청 접수와 결과 알림에는 버튼을 누른 Slack 사용자를 멘션합니다. 새 PR의
Assignee는 개인 토큰의 GitHub 계정이며 버전 라벨과 Android 팀 리뷰 요청도
추가합니다. 기존 PR을 재사용할 때는 담당자를 바꾸지 않습니다. 담당자를 지정할 수
있는지 GitHub 쓰기 전에 확인하고, 생성 후 실제 지정 결과도 확인합니다.
생성 또는 지정 도중 오류가 났다면 실행 로그와 이미 생성된 PR을 확인합니다.

## 병합 전 직접 테스트

테스트 채널은 `#마라팸`(`C082MGJUVHR`)이며 **SDG Version PR Test (local)**
앱(`A0C6WBJ3YR5`)을 사용합니다. 채널 상단 **워크플로** 탭에서 **SDG 버전 PR
테스트 (미리보기) (local)**를 실행하거나 `/SDG`로 선택합니다. 화면이 좁으면
**더 보기 → 워크플로**에서 찾습니다. 채팅 메시지 고정은 사용하지 않습니다.

미리보기 트리거(`Ft0C79R5NN6S`)와 함수는 이 테스트 채널만 허용합니다.
토큰 소유자를 확인한 뒤 같은 토큰으로 읽기 전용 GitHub 조회를 수행합니다.
미리보기용 자식 프로세스에만 `GH_TOKEN`을 일시적으로 전달하며 `.env`에는 저장하지
않습니다. 최신 릴리스부터 현재 main까지의 PR 내용 한 건과 예정 담당자를 표시합니다.
커밋·브랜치·PR·라벨·GitHub Actions 실행 요청은 만들지 않습니다.

이 미리보기는 개인 토큰의 인증·조회와 버전 계산을 확인합니다. 공개 저장소 조회의
성공만으로 Actions 쓰기 권한을 증명할 수 없으며 실제 생성·Assignee·결과 웹훅의
검증은 별도로 필요합니다. 테스트 실행과 토큰 입력은 담당자가 직접 수행합니다.

개발 앱은 이 Mac에서 `slack run`이 실행 중일 때만 동작합니다. 로컬 소스·설치
정보는 Git에서 제외된 `.slack/preview-app`에 보관하며 운영 앱과 분리합니다.
운영 코드 변경은 PR #460에서 검토하며 충분한 테스트 후 팀에서 병합합니다.

CLI로는 다음 읽기 전용 조회도 가능합니다. GitHub CLI 인증이 필요합니다.

```sh
node preview.cjs Songgyubin
node preview.cjs '' 37494776
```

첫 번째는 로그인, 두 번째는 고유 ID로 예정 담당자를 확인합니다. 저장소 GET 요청과
고유 ID의 공개 계정 조회만 허용하며 실제 GitHub 쓰기는 수행하지 않습니다.

## 운영 설치 및 적용

운영 앱은 [SDG Version PR](https://slack.com/apps/A0C7SN83G8G)
(`A0C7SN83G8G`)이며 기존 생성 트리거는 `Ft0C6H0H2CUF`입니다.
실행 권한은 `C093TU3CCBG` 채널 멤버에게만 설정되어 있습니다.
[운영 실행 링크](https://slack.com/shortcuts/Ft0C6H0H2CUF/5fc960098cfa9655c5c795b3accbe3ac)는
운영 준비가 끝나면 담당자가 공유합니다. 개인 토큰 방식의 운영 배포·직접 실행은
테스트 후 적용합니다.

1. 팀에서 PR 검토와 테스트를 마친 뒤 `.github/workflows/create-version-pr.yml`을
   main에 병합합니다. GitHub Actions 설정의 **Allow GitHub Actions to create and
   approve pull requests**가 허용되어 있어야 합니다.
2. [Slack CLI](https://docs.slack.dev/tools/slack-cli/guides/installing-the-slack-cli-for-mac-and-linux/)와
   Deno를 설치하고 `slack login`으로 shopl을 인증합니다.
3. 이 디렉터리에서 `slack deploy --team T016H73FHDH --app A0C7SN83G8G`로
   변경된 앱을 배포합니다. `commands`, `chat:write`, `pins:write` 권한을 사용하며
   토큰용 저장소나 OAuth 권한을 추가하지 않습니다.
4. 기존 트리거를 다음과 같이 갱신해 실제 요청자의 ID와 개인 입력창을 열
   interactivity를 전달합니다. 기존 링크와 채널 실행 권한은 유지합니다.

   ```sh
   slack trigger update --trigger-id Ft0C6H0H2CUF --trigger-def triggers/create-version-pr.ts --app A0C7SN83G8G
   slack trigger access --trigger-id Ft0C6H0H2CUF --info --app A0C7SN83G8G
   ```

5. 앱을 운영 채널에 초대하고 기존 `SLACK_SDG_ANDROID_WEB_HOOK`의 대상 채널을
   확인합니다. 토큰을 가진 팀원이 직접 버튼으로 생성과 중복 실행을 테스트합니다.

개인 안내는 `SDG_VERSION_PR_TRIGGER_URL`의 동일 환경 트리거를 workflow button으로
연결합니다. **SDG 버전 PR 안내**는 실행한 사람에게만 표시하며 GitHub 요청을 하지
않습니다. 공개 안내 게시·고정 트리거는 사람이 명시적으로 실행할 때만 동작합니다.
기본 배포·CI에서는 메시지 게시 또는 고정을 실행하지 않습니다. 상단 탭을 사용하는
현재 테스트에서는 안내 게시·고정 트리거를 실행하지 않습니다.

## 검증

```sh
deno task check
node ../../.github/scripts/version-pr-summary.test.cjs
node ../../.github/scripts/create-version-pr.test.cjs
```

PR의 `validate`는 읽기 권한으로 토큰 입력·취소·오류·요청자·담당자·버전 계산·중복
실행 검사와 실제 main의 `dryRun` 조회를 수행합니다. PR 실행에서는 운영 생성 작업과
Slack 결과 웹훅을 실행하지 않습니다. 자동화는 PR 생성까지만 수행합니다.
