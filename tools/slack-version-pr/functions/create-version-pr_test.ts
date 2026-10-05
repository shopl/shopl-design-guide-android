import assert from "node:assert/strict";
import { SlackFunctionTester } from "deno-slack-sdk/mod.ts";
import handler from "./create-version-pr.ts";

const { createContext } = SlackFunctionTester("sdg_create_version_pr");

Deno.test("채널 제한, 인증 설정, GitHub 요청과 오류 처리", async () => {
  const originalFetch = globalThis.fetch;
  let requests = 0;
  let notifications = 0;
  let notificationOK = true;
  let status = 204;
  let networkFailure = false;
  globalThis.fetch = (url, init) => {
    if (String(url) === "https://slack.com/api/chat.postMessage") {
      notifications++;
      const body = new URLSearchParams(String(init?.body));
      assert.equal(body.get("channel"), "C093TU3CCBG");
      assert.match(body.get("text") || "", /생성을 요청/);
      return Promise.resolve(Response.json({ ok: notificationOK }));
    }
    requests++;
    assert.equal(
      url,
      "https://api.github.com/repos/shopl/shopl-design-guide-android/actions/workflows/create-version-pr.yml/dispatches",
    );
    assert.equal(init?.method, "POST");
    assert.equal(
      new Headers(init?.headers).get("Authorization"),
      "Bearer test-token",
    );
    assert.deepEqual(JSON.parse(String(init?.body)), { ref: "main" });
    if (networkFailure) return Promise.reject(new Error("offline"));
    return Promise.resolve(new Response(null, { status }));
  };
  const run = (channel = "C093TU3CCBG", token = "test-token") =>
    handler(createContext({
      team_id: "T016H73FHDH",
      inputs: { channel_id: channel },
      env: { SDG_GITHUB_ACTIONS_TOKEN: token },
    }));
  try {
    assert.match(
      (await handler(
        createContext({
          inputs: { channel_id: "C093TU3CCBG" },
          team_id: "TOTHER",
        }),
      )).error || "",
      /채널/,
    );
    assert.match((await run("COTHER")).error || "", /채널/);
    assert.match((await run("C093TU3CCBG", "")).error || "", /설정/);
    assert.equal(requests, 0);
    assert.equal(notifications, 0);
    assert.match((await run()).outputs?.message || "", /생성을 요청/);
    assert.equal(requests, 1);
    assert.equal(notifications, 1);
    notificationOK = false;
    assert.match((await run()).error || "", /요청은 접수됐지만/);
    assert.equal(notifications, 2);
    status = 403;
    assert.match((await run()).error || "", /HTTP 403/);
    assert.equal(notifications, 2);
    networkFailure = true;
    assert.match((await run()).error || "", /확인하지 못/);
  } finally {
    globalThis.fetch = originalFetch;
  }
});
