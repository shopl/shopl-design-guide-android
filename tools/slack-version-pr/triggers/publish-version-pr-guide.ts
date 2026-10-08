import type { Trigger } from "deno-slack-api/types.ts";
import guide from "./version-pr-guide.ts";

const trigger: Trigger = {
  ...guide,
  name: "SDG 버전 PR 안내 게시·고정",
  description:
    "채널에 버튼 안내 메시지를 게시하고 고정합니다. 운영 담당자만 실행하세요.",
  inputs: { ...guide.inputs, publish_to_channel: { value: "true" } },
};

export default trigger;
