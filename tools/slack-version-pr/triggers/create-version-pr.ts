import type { Trigger } from "deno-slack-api/types.ts";
import { VersionPrWorkflow } from "../manifest.ts";

const trigger: Trigger<typeof VersionPrWorkflow.definition> = {
  type: "shortcut",
  name: "SDG 버전 PR 만들기",
  description: "add 라벨이 있으면 minor, 없으면 patch 버전 PR을 만듭니다.",
  workflow: "#/workflows/sdg_version_pr",
  inputs: { channel_id: { value: "{{data.channel_id}}" } },
};

export default trigger;
