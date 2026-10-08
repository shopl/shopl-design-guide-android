import { DefineWorkflow, Manifest, Schema } from "deno-slack-sdk/mod.ts";
import { CreateVersionPr } from "./functions/create-version-pr.ts";
import { VersionPrGuide } from "./functions/version-pr-guide.ts";

export const VersionPrWorkflow = DefineWorkflow({
  callback_id: "sdg_version_pr",
  title: "SDG 버전 PR 만들기",
  description: "이전 릴리스 이후 PR 라벨로 버전을 계산하고 버전 PR을 만듭니다.",
  input_parameters: {
    properties: { channel_id: { type: Schema.slack.types.channel_id } },
    required: ["channel_id"],
  },
});
VersionPrWorkflow.addStep(CreateVersionPr, {
  channel_id: VersionPrWorkflow.inputs.channel_id,
});

export const VersionPrGuideWorkflow = DefineWorkflow({
  callback_id: "sdg_version_pr_guide",
  title: "SDG 버전 PR 안내",
  description: "버튼과 채팅창 실행 방법을 안내합니다.",
  input_parameters: {
    properties: {
      channel_id: { type: Schema.slack.types.channel_id },
      user_id: { type: Schema.slack.types.user_id },
      publish_to_channel: { type: Schema.types.boolean, default: false },
    },
    required: ["channel_id", "user_id"],
  },
});
VersionPrGuideWorkflow.addStep(VersionPrGuide, {
  channel_id: VersionPrGuideWorkflow.inputs.channel_id,
  user_id: VersionPrGuideWorkflow.inputs.user_id,
  publish_to_channel: VersionPrGuideWorkflow.inputs.publish_to_channel,
});

export default Manifest({
  name: "SDG Version PR",
  description: "SDG Android 버전 PR 생성",
  icon: "../../app/src/main/res/mipmap-xxxhdpi/ic_launcher.png",
  functions: [CreateVersionPr, VersionPrGuide],
  workflows: [VersionPrWorkflow, VersionPrGuideWorkflow],
  outgoingDomains: ["api.github.com"],
  botScopes: ["commands", "chat:write", "pins:write"],
});
