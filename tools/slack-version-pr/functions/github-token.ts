export const tokenView = (preview = false) => ({
  type: "modal" as const,
  callback_id: "sdg_version_pr_token",
  notify_on_close: true,
  title: {
    type: "plain_text" as const,
    text: preview ? "버전 PR 미리보기" : "SDG 버전 PR 만들기",
  },
  submit: {
    type: "plain_text" as const,
    text: preview ? "미리보기" : "PR 생성 요청",
  },
  close: { type: "plain_text" as const, text: "취소" },
  blocks: [
    {
      type: "section" as const,
      text: {
        type: "mrkdwn" as const,
        text:
          (preview
            ? "*테스트: GitHub 조회만 수행하며 실제 PR을 생성하지 않습니다.*\n"
            : "개인 토큰으로 GitHub Actions 실행을 요청합니다.\n") +
          "<https://github.com/settings/personal-access-tokens/new?name=SDG+Version+PR&target_name=shopl&expires_in=30&actions=write|개인 토큰 생성>에서 *shopl/shopl-design-guide-android 저장소만* 선택하고 *Actions: Read and write* 권한으로 발급하세요.\n" +
          "토큰 소유자를 새 PR의 Assignee로 지정합니다. 토큰은 앱이 저장하지 않으며 실행할 때마다 입력합니다. 이 입력창은 본인에게만 표시됩니다.",
      },
    },
    {
      type: "input" as const,
      block_id: "github_token",
      label: { type: "plain_text" as const, text: "Personal access token" },
      element: {
        type: "plain_text_input" as const,
        action_id: "token",
        max_length: 255,
        placeholder: { type: "plain_text" as const, text: "github_pat_…" },
      },
    },
  ],
});

export const tokenError = (text: string) => ({
  response_action: "errors" as const,
  errors: { github_token: text },
});

export async function getTokenOwner(token: string): Promise<
  { id: number; login: string } | { error: string }
> {
  if (token.length > 255 || !/^(github_pat_|ghp_)[A-Za-z0-9_]+$/.test(token)) {
    return { error: "GitHub에서 발급한 개인 토큰을 입력해주세요." };
  }
  try {
    const profile = await fetch("https://api.github.com/user", {
      headers: {
        Accept: "application/vnd.github+json",
        Authorization: `Bearer ${token}`,
        "X-GitHub-Api-Version": "2022-11-28",
      },
      redirect: "error",
      signal: AbortSignal.timeout(3000),
    });
    if (!profile.ok) {
      return {
        error:
          `GitHub 계정 확인 실패: HTTP ${profile.status}. 토큰과 만료일을 확인해주세요.`,
      };
    }
    const { id, login, type } = await profile.json();
    if (
      type !== "User" || !Number.isSafeInteger(id) || id < 1 ||
      typeof login !== "string" ||
      !/^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(login)
    ) {
      return { error: "토큰 소유자의 GitHub 개인 계정을 확인하지 못했습니다." };
    }
    return { id, login };
  } catch {
    return { error: "GitHub 계정을 확인하지 못했습니다. 다시 시도해주세요." };
  }
}
