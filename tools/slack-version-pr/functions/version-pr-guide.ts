import { DefineFunction, Schema, SlackFunction } from "deno-slack-sdk/mod.ts";

export const VersionPrGuide = DefineFunction({
  callback_id: "sdg_show_version_pr_guide",
  title: "SDG 버전 PR 안내",
  source_file: "functions/version-pr-guide.ts",
  input_parameters: {
    properties: {
      channel_id: { type: Schema.slack.types.channel_id },
      user_id: { type: Schema.slack.types.user_id },
      publish_to_channel: { type: Schema.types.boolean, default: false },
    },
    required: ["channel_id", "user_id"],
  },
});

export default SlackFunction(
  VersionPrGuide,
  async ({ inputs, env, team_id, client }) => {
    if (team_id !== "T016H73FHDH" || inputs.channel_id !== "C093TU3CCBG") {
      return { error: "#sdg-android-deploy 채널에서 실행해주세요." };
    }
    const url = env.SDG_VERSION_PR_TRIGGER_URL;
    if (
      !url ||
      !/^https:\/\/slack\.com\/shortcuts\/Ft[A-Za-z0-9]+\/[A-Za-z0-9]+$/.test(
        url,
      )
    ) {
      return {
        error: "SDG_VERSION_PR_TRIGGER_URL에 버전 PR 생성 링크를 설정해주세요.",
      };
    }
    const message = {
      channel: inputs.channel_id,
      text:
        "SDG 버전 PR: 버튼을 누르거나 채팅창에 /SDG를 입력하고 ‘SDG 버전 PR 만들기’를 선택하세요.",
      blocks: [
        {
          type: "section" as const,
          text: {
            type: "mrkdwn" as const,
            text:
              "*SDG 버전 PR*\n이전 릴리스 이후 PR의 라벨을 확인해 `add`가 있으면 minor, 없으면 patch를 올립니다. 이미 열린 버전 PR이 있으면 해당 PR을 안내합니다.\n버튼을 누르거나 채팅창에 `/SDG`를 입력하고 *SDG 버전 PR 만들기*를 선택하세요. PR 리뷰·병합은 팀에서 진행합니다.",
          },
        },
        {
          type: "actions" as const,
          elements: [{
            type: "workflow_button" as const,
            action_id: "sdg_create_version_pr",
            text: { type: "plain_text" as const, text: "버전 PR 생성" },
            accessibility_label:
              "라벨로 버전을 계산하고 SDG 버전 PR 생성을 요청합니다",
            workflow: { trigger: { url } },
          }],
        },
      ],
    };
    let published = false;
    try {
      if (inputs.publish_to_channel !== true) {
        const result = await client.chat.postEphemeral({
          ...message,
          user: inputs.user_id,
        });
        return result.ok
          ? { outputs: {} }
          : { error: "개인 안내를 표시하지 못했습니다." };
      }
      const result = await client.chat.postMessage(message);
      if (!result.ok) return { error: "채널 안내를 게시하지 못했습니다." };
      published = true;
      const pin = await client.pins.add({
        channel: inputs.channel_id,
        timestamp: result.ts,
      });
      if (!pin.ok && pin.error !== "already_pinned") {
        return {
          error:
            "안내는 게시됐지만 고정하지 못했습니다. 재게시 대신 해당 메시지를 직접 고정해주세요.",
        };
      }
      return { outputs: {} };
    } catch {
      return {
        error: published
          ? "안내는 게시됐지만 고정 결과를 확인하지 못했습니다. 재게시 대신 해당 메시지의 고정 상태를 확인해주세요."
          : "안내 표시 결과를 확인하지 못했습니다. Slack 상태를 확인한 뒤 다시 시도해주세요.",
      };
    }
  },
);
