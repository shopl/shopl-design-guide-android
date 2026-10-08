import type { Trigger } from "deno-slack-api/types.ts";
import { VersionPrGuideWorkflow } from "../manifest.ts";

const trigger: Trigger<typeof VersionPrGuideWorkflow.definition> = {
  type: "shortcut",
  name: "SDG 버전 PR 안내",
  description: "실행한 사람에게만 버전 PR 버튼과 사용법을 보여줍니다.",
  workflow: "#/workflows/sdg_version_pr_guide",
  inputs: {
    channel_id: { value: "C093TU3CCBG" },
    user_id: { value: "{{data.user_id}}" },
    publish_to_channel: { value: "false" },
  },
};

export default trigger;
