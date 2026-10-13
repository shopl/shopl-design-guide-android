import { DefineFunction, Schema, SlackFunction } from "deno-slack-sdk/mod.ts";
import {
  resolveToken,
  tokenFeedback,
  tokenView,
  updateBranchExample,
} from "./github-token.ts";
import { deleteToken, loadToken } from "./token-store.ts";

export const CreateVersionPr = DefineFunction({
  callback_id: "sdg_create_version_pr",
  title: "SDG 버전 PR 만들기",
  source_file: "functions/create-version-pr.ts",
  input_parameters: {
    properties: {
      channel_id: { type: Schema.slack.types.channel_id },
      user_id: { type: Schema.slack.types.user_id },
      interactivity: { type: Schema.slack.types.interactivity },
    },
    required: ["channel_id", "user_id", "interactivity"],
  },
  output_parameters: {
    properties: { message: { type: Schema.types.string } },
    required: ["message"],
  },
});

const allowed = (team: string, channel: string, user: string) =>
  team === "T016H73FHDH" && channel === "C093TU3CCBG" &&
  /^[UW][A-Z0-9]+$/.test(user);

export default SlackFunction(
  CreateVersionPr,
  async ({ inputs, client, team_id }) => {
    if (!allowed(team_id, inputs.channel_id, inputs.user_id)) {
      return { error: "#sdg-android-deploy 채널에서 실행해주세요." };
    }
    if (inputs.interactivity?.interactor?.id !== inputs.user_id) {
      return { error: "버튼을 누른 Slack 사용자를 확인하지 못했습니다." };
    }
    try {
      const saved = await loadToken(client, team_id, inputs.user_id);
      const opened = await client.views.open({
        interactivity_pointer: inputs.interactivity.interactivity_pointer,
        view: tokenView(false, saved),
      });
      if (!opened.ok) {
        return {
          error: "개인 토큰 입력창을 열지 못했습니다. 다시 실행해주세요.",
        };
      }
      return { completed: false };
    } catch {
      return {
        error: "개인 토큰 입력창을 열지 못했습니다. 다시 실행해주세요.",
      };
    }
  },
).addViewSubmissionHandler(
  "sdg_version_pr_token",
  async ({ inputs, client, body, view, team_id, env }) => {
    if (
      !allowed(team_id, inputs.channel_id, inputs.user_id) ||
      body.user.id !== inputs.user_id
    ) {
      return tokenFeedback(
        view,
        "버튼을 누른 본인의 요청만 실행할 수 있습니다.",
      );
    }
    // 원문 토큰은 개인 입력 또는 복호화 결과로만 읽으며 함수 결과·메타데이터·로그에 넣지 않습니다.
    const provided = view.state.values.github_token?.token?.value?.trim() || "";
    const name = view.state.values.branch_author?.name?.value?.trim()
      .toLowerCase() || "";
    const resolved = await resolveToken(
      client,
      env.SDG_TOKEN_ENCRYPTION_KEY || "",
      team_id,
      inputs.user_id,
      provided,
      name,
    );
    if ("error" in resolved) {
      return tokenFeedback(
        view,
        resolved.error,
        false,
        resolved.reset,
        resolved.field,
      );
    }
    const { token, owner, branchAuthor } = resolved;
    try {
      const response = await fetch(
        "https://api.github.com/repos/shopl/shopl-design-guide-android/actions/workflows/create-version-pr.yml/dispatches",
        {
          method: "POST",
          headers: {
            Accept: "application/vnd.github+json",
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-GitHub-Api-Version": "2022-11-28",
          },
          body: JSON.stringify({
            ref: "main",
            inputs: {
              requester_slack_id: inputs.user_id,
              assignee_id: String(owner.id),
              branch_author: branchAuthor,
            },
          }),
          redirect: "error",
          signal: AbortSignal.timeout(3000),
        },
      );
      if (![200, 204].includes(response.status)) {
        if (response.status === 401) {
          return tokenFeedback(
            view,
            "토큰이 만료되거나 폐기됐습니다. 새 토큰을 입력해주세요.",
            false,
            true,
          );
        }
        return tokenFeedback(
          view,
          `GitHub 실행 요청 실패: HTTP ${response.status}. main의 워크플로 설치, 토큰의 SDG 저장소 선택·Actions 쓰기 권한·조직 승인을 확인해주세요.`,
        );
      }
      const message =
        `<@${inputs.user_id}>님이 SDG 버전 PR 생성을 요청했습니다. 브랜치 작성자 이름은 ${branchAuthor}, 새 PR의 담당자는 ${owner.login}입니다. 완료되면 이 채널에 PR 링크를 알려드립니다.`;
      const completed = await client.functions.completeSuccess({
        function_execution_id: body.function_data.execution_id,
        outputs: { message },
      });
      if (!completed.ok) {
        return tokenFeedback(
          view,
          "GitHub 요청은 접수됐지만 Slack 안내를 이어가지 못했습니다. GitHub Actions 실행 결과를 먼저 확인해주세요.",
        );
      }
      return { response_action: "clear" as const };
    } catch {
      return tokenFeedback(
        view,
        "GitHub 요청의 접수 여부를 확인하지 못했습니다. GitHub Actions 실행 결과를 확인한 뒤 다시 시도해주세요.",
      );
    }
  },
).addBlockActionsHandler(
  { block_id: "branch_author", action_id: "name" },
  async ({ inputs, client, body, action, team_id }) => {
    if (
      !allowed(team_id, inputs.channel_id, inputs.user_id) ||
      body.user.id !== inputs.user_id ||
      body.view?.callback_id !== "sdg_version_pr_token"
    ) return;
    await updateBranchExample(client, body.view, action.value || "");
  },
).addBlockActionsHandler(
  ["sdg_token_change", "sdg_token_delete"],
  async ({ inputs, client, body, action, team_id }) => {
    if (
      !allowed(team_id, inputs.channel_id, inputs.user_id) ||
      body.user.id !== inputs.user_id || !body.view
    ) return;
    try {
      if (action.action_id === "sdg_token_delete") {
        await deleteToken(
          client,
          team_id,
          inputs.user_id,
        );
      }
      const updated = await client.views.update({
        view_id: body.view.id,
        hash: body.view.hash,
        view: tokenView(
          false,
          null,
          action.action_id === "sdg_token_delete"
            ? "앱에 저장된 토큰을 삭제했습니다."
            : "새 토큰으로 변경합니다. 새 토큰을 확인하기 전까지 기존 토큰은 유지합니다.",
          body.view.state.values.branch_author?.name?.value || "",
        ),
      });
      if (!updated.ok) throw new Error();
    } catch {
      await client.functions.completeError({
        function_execution_id: body.function_data.execution_id,
        error:
          "토큰 변경·삭제 화면을 갱신하지 못했습니다. 창을 닫고 다시 실행해주세요.",
      });
    }
  },
).addViewClosedHandler(
  "sdg_version_pr_token",
  async ({ inputs, client, body, team_id }) => {
    if (
      !allowed(team_id, inputs.channel_id, inputs.user_id) ||
      body.user.id !== inputs.user_id
    ) return;
    await client.functions.completeSuccess({
      function_execution_id: body.function_data.execution_id,
      outputs: { message: "" },
    });
  },
);
