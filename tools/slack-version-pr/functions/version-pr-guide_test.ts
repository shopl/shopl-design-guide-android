import assert from "node:assert/strict";
import { SlackFunctionTester } from "deno-slack-sdk/mod.ts";
import handler, { VersionPrGuide } from "./version-pr-guide.ts";
import { VersionPrGuideWorkflow } from "../manifest.ts";
import privateGuide from "../triggers/version-pr-guide.ts";
import publicGuide from "../triggers/publish-version-pr-guide.ts";

const { createContext } = SlackFunctionTester("sdg_show_version_pr_guide");
const triggerUrl = "https://slack.com/shortcuts/Ft123/abc123";

Deno.test("개인 안내는 공개 게시 없이 같은 버튼을 표시하고 명시적 게시만 고정", async () => {
  assert.notEqual(
    VersionPrGuide.definition.callback_id,
    VersionPrGuideWorkflow.definition.callback_id,
  );
  assert.equal(privateGuide.workflow, publicGuide.workflow);
  assert.equal(privateGuide.inputs?.publish_to_channel?.value, "false");
  assert.equal(publicGuide.inputs?.publish_to_channel.value, "true");
  const originalFetch = globalThis.fetch;
  const methods: string[] = [];
  let messageOK = true;
  let pinOK = true;
  let networkFailure = "";
  globalThis.fetch = (url, init) => {
    const method = String(url).replace("https://slack.com/api/", "");
    assert.ok(
      ["chat.postMessage", "chat.postEphemeral", "pins.add"].includes(method),
    );
    methods.push(method);
    const body = new URLSearchParams(String(init?.body));
    assert.equal(body.get("channel"), "C093TU3CCBG");
    if (method === networkFailure) return Promise.reject(new Error("offline"));
    if (method === "pins.add") {
      assert.equal(body.get("timestamp"), "123.456");
      return Promise.resolve(Response.json({ ok: pinOK }));
    }
    if (method === "chat.postEphemeral") {
      assert.equal(body.get("user"), "UTEST");
    }
    const blocks = JSON.parse(body.get("blocks") || "[]");
    assert.match(blocks[0].text.text, /`\/SDG`/);
    assert.match(blocks[0].text.text, /minor.*patch/);
    assert.equal(blocks[1].elements[0].type, "workflow_button");
    assert.equal(blocks[1].elements[0].workflow.trigger.url, triggerUrl);
    assert.equal(blocks[1].elements[0].text.text, "버전 PR 생성");
    assert.ok(blocks[1].elements[0].accessibility_label);
    return Promise.resolve(Response.json({ ok: messageOK, ts: "123.456" }));
  };
  const run = (
    publish?: boolean,
    url = triggerUrl,
    channel = "C093TU3CCBG",
    team = "T016H73FHDH",
  ) =>
    handler(createContext({
      team_id: team,
      inputs: {
        channel_id: channel,
        user_id: "UTEST",
        publish_to_channel: publish,
      },
      env: { SDG_VERSION_PR_TRIGGER_URL: url },
    }));
  try {
    assert.match((await run(false, triggerUrl, "COTHER")).error || "", /채널/);
    assert.match(
      (await run(false, triggerUrl, "C093TU3CCBG", "TOTHER")).error || "",
      /채널/,
    );
    for (
      const url of [
        "",
        "https://example.com/shortcuts/Ft123/abc123",
        "https://slack.com/shortcuts/Ft123/abc123?redirect=other",
      ]
    ) {
      assert.match((await run(false, url)).error || "", /링크/);
    }
    assert.deepEqual(methods, []);
    assert.equal((await run()).error, undefined);
    assert.equal((await run(false)).error, undefined);
    assert.deepEqual(methods.splice(0), [
      "chat.postEphemeral",
      "chat.postEphemeral",
    ]);
    assert.equal((await run(true)).error, undefined);
    assert.deepEqual(methods.splice(0), ["chat.postMessage", "pins.add"]);
    messageOK = false;
    assert.match((await run()).error || "", /개인 안내/);
    assert.deepEqual(methods.splice(0), ["chat.postEphemeral"]);
    assert.match((await run(true)).error || "", /게시하지 못/);
    assert.deepEqual(methods.splice(0), ["chat.postMessage"]);
    messageOK = true;
    pinOK = false;
    assert.match((await run(true)).error || "", /재게시 대신/);
    assert.deepEqual(methods.splice(0), ["chat.postMessage", "pins.add"]);
    networkFailure = "pins.add";
    assert.match((await run(true)).error || "", /안내는 게시됐지만/);
    assert.deepEqual(methods.splice(0), ["chat.postMessage", "pins.add"]);
    networkFailure = "chat.postEphemeral";
    assert.match((await run()).error || "", /표시 결과/);
    assert.deepEqual(methods, ["chat.postEphemeral"]);
  } finally {
    globalThis.fetch = originalFetch;
  }
});
