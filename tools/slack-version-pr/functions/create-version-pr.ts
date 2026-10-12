import { DefineFunction, Schema, SlackFunction } from "deno-slack-sdk/mod.ts";

export const CreateVersionPr = DefineFunction({
  callback_id: "sdg_create_version_pr",
  title: "SDG 버전 PR 만들기",
  source_file: "functions/create-version-pr.ts",
  input_parameters: {
    properties: {
      channel_id: { type: Schema.slack.types.channel_id },
      user_id: { type: Schema.slack.types.user_id },
    },
    required: ["channel_id", "user_id"],
  },
  output_parameters: {
    properties: { message: { type: Schema.types.string } },
    required: ["message"],
  },
});

export default SlackFunction(
  CreateVersionPr,
  async ({ inputs, env, team_id, client }) => {
    if (team_id !== "T016H73FHDH" || inputs.channel_id !== "C093TU3CCBG") {
      return { error: "#sdg-android-deploy 채널에서 실행해주세요." };
    }
    if (!env.SDG_GITHUB_ACTIONS_TOKEN) {
      return { error: "SDG_GITHUB_ACTIONS_TOKEN 설정이 필요합니다." };
    }
    if (!/^[UW][A-Z0-9]+$/.test(inputs.user_id)) {
      return { error: "버튼을 누른 Slack 사용자를 확인하지 못했습니다." };
    }
    let assignee;
    try {
      const users = JSON.parse(env.SDG_GITHUB_USER_MAP || "{}");
      if (!users || typeof users !== "object" || Array.isArray(users)) {
        throw new Error("invalid user map");
      }
      assignee = users[inputs.user_id];
    } catch {
      return { error: "SDG_GITHUB_USER_MAP의 계정 연결 설정을 확인해주세요." };
    }
    if (
      typeof assignee !== "string" ||
      !/^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(assignee)
    ) {
      return {
        error:
          "Slack 사용자에 연결된 GitHub 계정을 SDG_GITHUB_USER_MAP에 등록해주세요.",
      };
    }
    try {
      const response = await fetch(
        "https://api.github.com/repos/shopl/shopl-design-guide-android/actions/workflows/create-version-pr.yml/dispatches",
        {
          method: "POST",
          headers: {
            Accept: "application/vnd.github+json",
            Authorization: `Bearer ${env.SDG_GITHUB_ACTIONS_TOKEN}`,
            "Content-Type": "application/json",
            "X-GitHub-Api-Version": "2022-11-28",
          },
          body: JSON.stringify({
            ref: "main",
            inputs: { requester_slack_id: inputs.user_id, assignee },
          }),
          signal: AbortSignal.timeout(15000),
        },
      );
      if (response.status !== 204) {
        return {
          error:
            `GitHub 실행 요청 실패: HTTP ${response.status}. 워크플로 설치와 Actions 권한을 확인해주세요.`,
        };
      }
      const message =
        `<@${inputs.user_id}>님이 SDG 버전 PR 생성을 요청했습니다. 새 PR의 담당자는 ${assignee}입니다. 완료되면 이 채널에 PR 링크를 알려드립니다.`;
      const notification = await client.chat.postMessage({
        channel: inputs.channel_id,
        text: message,
      });
      if (!notification.ok) {
        return {
          error:
            "GitHub 요청은 접수됐지만 Slack 알림을 보내지 못했습니다. 앱의 채널 참여와 권한을 확인해주세요.",
        };
      }
      return { outputs: { message } };
    } catch {
      return {
        error:
          "실행 요청 또는 Slack 알림 전송을 확인하지 못했습니다. 실행 결과를 확인한 뒤 다시 시도해주세요.",
      };
    }
  },
);
