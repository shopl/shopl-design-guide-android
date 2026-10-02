import { DefineWorkflow, Manifest, Schema } from "deno-slack-sdk/mod.ts";
import { CreateVersionPr } from "./functions/create-version-pr.ts";

export const VersionPrWorkflow = DefineWorkflow({
  callback_id: "sdg_version_pr",
  title: "SDG 버전 PR 만들기",
  description: "이전 릴리스 이후 PR 라벨로 버전을 계산하고 버전 PR을 만듭니다.",
  input_parameters: {
    properties: { channel_id: { type: Schema.slack.types.channel_id } },
    required: ["channel_id"],
  },
});
const request = VersionPrWorkflow.addStep(CreateVersionPr, {
  channel_id: VersionPrWorkflow.inputs.channel_id,
});
VersionPrWorkflow.addStep(Schema.slack.functions.SendMessage, {
  channel_id: "C093TU3CCBG",
  message: request.outputs.message,
});

export default Manifest({
  name: "SDG 버전 PR",
  description: "SDG Android 버전 PR 생성",
  icon: "../../app/src/main/res/mipmap-xxxhdpi/ic_launcher.png",
  functions: [CreateVersionPr],
  workflows: [VersionPrWorkflow],
  outgoingDomains: ["api.github.com"],
  botScopes: ["commands", "chat:write"],
});
