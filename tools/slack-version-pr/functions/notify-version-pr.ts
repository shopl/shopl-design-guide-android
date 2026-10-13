import { DefineFunction, Schema, SlackFunction } from "deno-slack-sdk/mod.ts";

export const NotifyVersionPr = DefineFunction({
  callback_id: "sdg_version_pr_notify",
  title: "버전 PR 요청 결과 안내",
  source_file: "functions/notify-version-pr.ts",
  input_parameters: {
    properties: {
      channel_id: { type: Schema.slack.types.channel_id },
      message: { type: Schema.types.string },
    },
    required: ["channel_id", "message"],
  },
});

export default SlackFunction(
  NotifyVersionPr,
  async ({ inputs, client, team_id }) => {
    if (
      team_id !== "T016H73FHDH" ||
      !["C093TU3CCBG", "C082MGJUVHR"].includes(inputs.channel_id)
    ) {
      return {
        error: "SDG 운영 채널 또는 지정된 테스트 채널에서 실행해주세요.",
      };
    }
    if (!inputs.message) return { outputs: {} };
    try {
      const sent = await client.chat.postMessage({
        channel: inputs.channel_id,
        text: inputs.message,
      });
      if (!sent.ok) {
        return {
          error:
            "GitHub 처리는 완료됐지만 Slack 알림을 보내지 못했습니다. 실행 결과를 확인해주세요.",
        };
      }
      return { outputs: {} };
    } catch {
      return {
        error:
          "GitHub 처리 후 Slack 알림 전송을 확인하지 못했습니다. 실행 결과를 확인해주세요.",
      };
    }
  },
);
