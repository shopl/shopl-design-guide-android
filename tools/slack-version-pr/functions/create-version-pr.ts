import { DefineFunction, Schema, SlackFunction } from "deno-slack-sdk/mod.ts";

export const CreateVersionPr = DefineFunction({
  callback_id: "sdg_create_version_pr",
  title: "SDG 버전 PR 만들기",
  source_file: "functions/create-version-pr.ts",
  input_parameters: {
    properties: { channel_id: { type: Schema.slack.types.channel_id } },
    required: ["channel_id"],
  },
  output_parameters: {
    properties: { message: { type: Schema.types.string } },
    required: ["message"],
  },
});

export default SlackFunction(
  CreateVersionPr,
  async ({ inputs, env, team_id }) => {
    if (team_id !== "T016H73FHDH" || inputs.channel_id !== "C093TU3CCBG") {
      return { error: "#sdg-android-deploy 채널에서 실행해주세요." };
    }
    if (!env.SDG_GITHUB_ACTIONS_TOKEN) {
      return { error: "SDG_GITHUB_ACTIONS_TOKEN 설정이 필요합니다." };
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
          body: JSON.stringify({ ref: "main" }),
          signal: AbortSignal.timeout(15000),
        },
      );
      if (response.status !== 204) {
        return {
          error:
            `GitHub 실행 요청 실패: HTTP ${response.status}. 워크플로 설치와 Actions 권한을 확인해주세요.`,
        };
      }
      return {
        outputs: {
          message:
            "SDG 버전 PR 생성을 요청했습니다. 완료되면 이 채널에 PR 링크를 알려드립니다.",
        },
      };
    } catch {
      return {
        error:
          "GitHub 실행 요청을 확인하지 못했습니다. 잠시 후 다시 실행해주세요.",
      };
    }
  },
);
