import type { SlackAPI } from "deno-slack-api/mod.ts";
import type { View } from "deno-slack-sdk/functions/interactivity/view_types.ts";
import {
  decryptToken,
  isBranchAuthor,
  loadToken,
  saveToken,
} from "./token-store.ts";

const branchExample = (value: string) => {
  const name = value.trim().toLowerCase();
  return {
    type: "context" as const,
    block_id: "branch_example",
    elements: [{
      type: "mrkdwn" as const,
      text:
        `브랜치명 예시: \`version/${isBranchAuthor(name) ? name : "이름"}/patch-1.23.3\`\n실제 버전은 릴리스 변경사항에 따라 결정됩니다.`,
    }],
  };
};

export const tokenView = (
  preview = false,
  saved: { github_login: string; branch_author?: string } | null = null,
  notice = "",
  branchAuthor = saved?.branch_author || "",
) => ({
  type: "modal" as const,
  callback_id: "sdg_version_pr_token",
  notify_on_close: true,
  private_metadata: saved?.github_login || "",
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
    ...(notice
      ? [{
        type: "section" as const,
        text: { type: "plain_text" as const, text: notice },
      }]
      : []),
    {
      type: "section" as const,
      text: {
        type: "mrkdwn" as const,
        text:
          (preview
            ? "*테스트: GitHub 조회만 수행하며 실제 PR을 생성하지 않습니다.*\n"
            : "개인 토큰으로 GitHub Actions 실행을 요청합니다.\n") +
          "<https://github.com/settings/personal-access-tokens/new?name=SDG+Version+PR&target_name=shopl&expires_in=30&actions=write|개인 토큰 생성>에서 *shopl/shopl-design-guide-android 저장소만* 선택하고 *Actions: Read and write* 권한으로 발급하세요.\n" +
          "토큰 소유자를 새 PR의 Assignee로 지정합니다. 브랜치는 `version/입력한 이름/minor-버전` 또는 `version/입력한 이름/patch-버전`으로 생성합니다. 토큰은 Slack 사용자별로 암호화해 보관하고 다음 실행부터 재사용합니다. 만료·폐기되면 새 토큰을 입력합니다. 이 화면은 본인에게만 표시됩니다.",
      },
    },
    {
      type: "input" as const,
      block_id: "branch_author",
      dispatch_action: true,
      label: { type: "plain_text" as const, text: "브랜치 작성자 이름" },
      hint: {
        type: "plain_text" as const,
        text:
          "scott처럼 영문 소문자로 시작하고 숫자·하이픈을 사용할 수 있습니다. 저장한 이름은 다음 실행에 자동으로 채워집니다.",
      },
      element: {
        type: "plain_text_input" as const,
        action_id: "name",
        dispatch_action_config: {
          trigger_actions_on: ["on_character_entered"],
        },
        min_length: 1,
        max_length: 32,
        placeholder: { type: "plain_text" as const, text: "scott" },
        ...(branchAuthor ? { initial_value: branchAuthor } : {}),
      },
    },
    branchExample(branchAuthor),
    ...(saved
      ? [
        {
          type: "section" as const,
          text: {
            type: "mrkdwn" as const,
            text:
              `*GitHub 계정: ${saved.github_login}*\n저장된 토큰을 사용합니다. 토큰을 다시 입력하지 않고 아래 실행 버튼을 누르세요.`,
          },
        },
        {
          type: "actions" as const,
          elements: [
            {
              type: "button" as const,
              action_id: "sdg_token_change",
              text: { type: "plain_text" as const, text: "토큰 변경" },
            },
            {
              type: "button" as const,
              action_id: "sdg_token_delete",
              text: { type: "plain_text" as const, text: "저장된 토큰 삭제" },
              style: "danger" as const,
            },
          ],
        },
      ]
      : [{
        type: "input" as const,
        block_id: "github_token",
        label: { type: "plain_text" as const, text: "Personal access token" },
        element: {
          type: "plain_text_input" as const,
          action_id: "token",
          max_length: 255,
          placeholder: { type: "plain_text" as const, text: "github_pat_…" },
        },
      }]),
  ],
});

export async function updateBranchExample(
  client: ReturnType<typeof SlackAPI>,
  view: View,
  name: string,
) {
  try {
    for (let attempt = 0; attempt < 2; attempt++) {
      const updated = await client.views.update({
        view_id: view.id,
        hash: view.hash,
        view: {
          type: "modal",
          callback_id: view.callback_id,
          title: view.title,
          close: view.close,
          submit: view.submit,
          notify_on_close: view.notify_on_close,
          private_metadata: view.private_metadata,
          blocks: view.blocks.map((block) =>
            block.block_id === "branch_example" ? branchExample(name) : block
          ),
        },
      });
      if (updated.ok) return;
      if (
        updated.error !== "hash_conflict" ||
        updated.view?.callback_id !== "sdg_version_pr_token" ||
        !updated.view.state?.values.branch_author?.name
      ) return;
      view = updated.view as View;
      name = view.state.values.branch_author.name.value || "";
    }
  } catch {
    console.warn("브랜치명 예시를 갱신하지 못했습니다.");
  }
}

export const tokenError = (
  text: string,
  field: "github_token" | "branch_author" = "github_token",
) => ({
  response_action: "errors" as const,
  errors: { [field]: text },
});

export async function getTokenOwner(token: string): Promise<
  { id: number; login: string } | { error: string; status?: number }
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
        status: profile.status,
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

export const tokenFeedback = (
  view: {
    private_metadata?: string;
    state?: {
      values: { branch_author?: { name?: { value?: string | null } } };
    };
  },
  text: string,
  preview = false,
  reset = false,
  field: "github_token" | "branch_author" = "github_token",
) => {
  if (
    (field === "branch_author" && view.state?.values.branch_author) ||
    (field === "github_token" && !reset && !view.private_metadata)
  ) return tokenError(text, field);
  const saved = !reset &&
      /^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(
        view.private_metadata || "",
      )
    ? { github_login: view.private_metadata! }
    : null;
  return {
    response_action: "update" as const,
    view: tokenView(
      preview,
      saved,
      text,
      view.state?.values.branch_author?.name?.value || "",
    ),
  };
};

export async function resolveToken(
  client: ReturnType<typeof SlackAPI>,
  key: string,
  team: string,
  user: string,
  provided: string,
  branchAuthor: string,
): Promise<
  { token: string; owner: { id: number; login: string }; branchAuthor: string } | {
    error: string;
    reset?: boolean;
    field?: "branch_author";
  }
> {
  if (!isBranchAuthor(branchAuthor)) {
    return {
      error:
        "브랜치 작성자 이름은 영문 소문자로 시작하고 소문자·숫자·하이픈으로 32자 이내로 입력해주세요. 예: scott",
      field: "branch_author",
    };
  }
  try {
    const saved = provided ? null : await loadToken(client, team, user);
    if (!provided && !saved) {
      return {
        error: "저장된 토큰이 없습니다. 새 토큰을 입력해주세요.",
        reset: true,
      };
    }
    const token = provided || await decryptToken(saved!, key);
    const owner = await getTokenOwner(token);
    if ("error" in owner) {
      if (!provided && owner.status === 401) {
        // 다른 창에서 갱신한 토큰을 지우지 않도록 재입력 화면만 표시합니다.
        return {
          error:
            "저장된 토큰이 만료되거나 폐기됐습니다. 새 토큰을 입력해주세요.",
          reset: true,
        };
      }
      return owner;
    }
    if (saved && owner.id !== saved.github_id) {
      return {
        error:
          "저장된 토큰의 GitHub 계정이 일치하지 않습니다. 토큰을 변경해주세요.",
      };
    }
    if (provided || saved?.branch_author !== branchAuthor) {
      await saveToken(client, key, team, user, token, owner, branchAuthor);
    }
    return { token, owner, branchAuthor };
  } catch {
    return {
      error:
        "개인 토큰을 읽거나 암호화해 저장하지 못했습니다. 앱의 암호화 키와 저장소 설정을 확인해주세요.",
    };
  }
}
