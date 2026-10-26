# Android SDG 플러그인

Android 전문 개발자가 아니어도 에이전트가 SDG의 실제 API를 조회하고 기존 UI를 재사용하도록 돕습니다. MCP 서버 1개, 도구 6개, 스킬 3개를 제공합니다.

**한 번 연결하면 MCP가 도구 호출 전에 최신 SDG 정보를 자동으로 확인하고 내려받습니다.** 일반적인 SDG 업데이트에는 사용자 재설치, 수동 갱신, Claude 자동 업데이트 설정이 필요 없습니다. 별도 상시 서버도 필요 없습니다.

현재 자동 갱신 코드는 로컬 검증 상태입니다. 공식 저장소에 workflow를 반영하고 최초 데이터를 게시해야 실제 사용자 환경에서 작동합니다. 게시 전에는 최신 확인 실패(HTTP 404)를 표시하고 설치 시 포함된 정보를 사용합니다.

## 최초 준비

- Node.js 22 이상이 필요합니다. MCP 실행에는 npm install, Android SDK, JDK가 필요 없습니다. 앱 컴파일에는 해당 앱의 개발 환경이 필요합니다.
- GitHub에 접근할 수 있는 네트워크가 필요합니다. 공개 데이터 조회에 개인 GitHub 인증은 필요 없습니다.
- MCP는 앱 코드를 직접 수정하지 않습니다. 에이전트가 앱의 SDG 버전을 확인하고 필요한 수정과 컴파일을 수행합니다.

## Codex 설치

공식 저장소 main에 플러그인이 반영된 뒤 사용할 수 있습니다.

```sh
codex plugin marketplace add shopl/shopl-design-guide-android --ref main
codex plugin add sdg-android@sdg-android-marketplace
```

설치 후 새 세션을 시작합니다. 이미 `codex mcp add`로 수동 등록했다면 기존 설정을 보관하고, 플러그인 설치 후 `codex mcp remove sdg-android`로 해당 수동 등록만 제거합니다. 같은 이름의 수동 설정이 플러그인 연결을 대신할 수 있습니다.

## Claude Code / Cursor 설치

Claude Code:

```text
/plugin marketplace add shopl/shopl-design-guide-android
/plugin install sdg-android@sdg-android-marketplace
```

Cursor는 Customize에서 GitHub Repository 가져오기로 `shopl/shopl-design-guide-android`를 등록하고 `sdg-android`를 설치합니다. 조직 정책에 따른 설치 허용은 관리자가 설정합니다.

설치 후 새 세션에서 연결을 확인합니다. 이후 SDG 정보는 클라이언트 종류와 관계없이 MCP가 직접 갱신합니다. Claude Code·Cursor의 실제 설치 UI 검증은 별도 항목입니다.

## 파일 설치 / 플러그인을 지원하지 않는 클라이언트

공식 저장소 반영 전에는 담당자가 제공한 설치 압축 파일을 사용합니다. 압축 파일과 `.sha256` 파일을 같은 폴더에 두고 `<SDG버전>`과 경로를 실제 값으로 바꿉니다.

```sh
shasum -a 256 -c sdg-android-plugin-<SDG버전>.tar.gz.sha256
mkdir -p /absolute/path/to/sdg-android-plugin
tar -xzf sdg-android-plugin-<SDG버전>.tar.gz -C /absolute/path/to/sdg-android-plugin
codex plugin marketplace add /absolute/path/to/sdg-android-plugin
codex plugin add sdg-android@sdg-android-marketplace
```

플러그인을 지원하지 않는 클라이언트는 서버를 절대 경로로 등록합니다.

```json
{
  "mcpServers": {
    "sdg-android": {
      "command": "node",
      "args": ["/absolute/path/to/sdg-android-plugin/plugins/sdg-android/server/index.mjs"]
    }
  }
}
```

이 방식도 SDG 정보 자동 갱신을 지원합니다. 스킬은 `plugins/sdg-android/skills/`의 세 폴더를 클라이언트가 지원하는 스킬 위치에 설치합니다. `spawn node ENOENT`가 나오면 Node 22 이상 실행 파일의 절대 경로를 command에 지정합니다.

## 연결과 자동 갱신 확인

에이전트에게 다음을 요청합니다.

> SDG 연결을 확인해줘. sdg_resolve_context를 실제로 호출해서 SDG 버전, catalogId, update 상태를 알려줘. SDGBoxButton의 정확한 API도 조회해줘.

도구 호출 기록과 응답을 확인합니다. 말로만 답한 버전은 연결 증거가 아닙니다.

| update.state | 의미 |
| --- | --- |
| current | 게시 경로의 최신 검증 정보와 일치함을 확인 |
| offline | 네트워크·파일 검증 등에 실패하여 마지막 검증본 사용. 최신 여부는 미확인 |
| remote_behind | 원격에서 더 오래된 정보가 보여 적용하지 않음 |
| client_update_required | 새 정보 형식을 읽기 위해 MCP 프로그램 갱신 필요 |
| disabled | 검증용 고정 설정으로 자동 갱신을 끈 상태 |

SDG 배포 이후 검증·게시와 GitHub 전파가 완료되어야 새 정보가 보입니다. current는 공개된 최신 MCP 정보와 일치한다는 뜻이며, 아직 게시 검증 중인 Maven 배포까지 반영됐다는 뜻은 아닙니다. 앱 SDG가 오래됐으면 에이전트가 중앙 버전 선언을 맞추고 실제 의존성 해석·컴파일을 확인합니다.

`release.json`은 설치 당시의 프로그램과 포함된 SDG 정보를 기록합니다. 자동 갱신 후 MCP가 더 새로운 SDG 버전을 반환하는 것이 정상입니다. 조회 가능한 버전은 하나이며 과거 버전 선택 기능은 없습니다.

## MCP 프로그램·스킬 업데이트: 별도 대안

SDG 정보는 자동 갱신됩니다. MCP 실행 코드나 스킬 자체를 바꿀 때만 플러그인 업데이트가 필요합니다. 호환되지 않는 데이터 형식은 client_update_required로 알립니다. 원격 JSON에서 프로그램을 받아 실행하지 않습니다.

- Codex GitHub 설치: `codex plugin marketplace upgrade sdg-android-marketplace` 후 새 세션. 필요하면 해당 플러그인을 제거하고 재설치합니다.
- Claude Code: `/plugin` → Marketplaces → sdg-android-marketplace → Enable auto-update를 한 번 켜두는 방식을 대안으로 사용할 수 있습니다. 수동 갱신은 `claude plugin marketplace update sdg-android-marketplace`, `claude plugin update sdg-android@sdg-android-marketplace`입니다.
- 파일 설치: 새 압축 파일을 별도 폴더에 풀고 확인한 뒤 해당 플러그인과 마켓플레이스 등록만 제거하고 새 경로를 등록합니다. 수동 복사한 스킬도 함께 바꾸고 서버를 재시작합니다.

## 에이전트 사용 방식

- sdg-android-setup: 자동 갱신 상태와 앱 의존성 확인.
- sdg-android-components: 일반적인 Compose 화면 요청에서도 component·template·앱 kit 우선 재사용, 정확한 API·예제 조회.
- sdg-android-foundation: 토큰·아이콘의 실제 Kotlin 참조 확인.

도구는 sdg_resolve_context, sdg_search_components, sdg_get_component, sdg_get_example, sdg_search_assets, sdg_get_recipe입니다. 앱 kit은 MCP에 포함되지 않으므로 앱 저장소의 선언과 호출부를 직접 검색합니다. 기존 AGENTS.md 지침과 함께 사용합니다.

## 유지보수

SDG Sonatype PUBLISHED → 배포 소스·아티팩트 대조와 컴파일 → MCP 진단 → sdg-mcp-data 브랜치에 JSON 자동 게시 → 다음 도구 호출에서 자동 반영하는 구조입니다. 자세한 운영 절차는 저장소의 mcp/README.md를 참고합니다.
