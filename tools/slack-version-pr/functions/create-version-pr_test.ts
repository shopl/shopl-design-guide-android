import assert from "node:assert/strict";
import { SlackFunctionTester } from "deno-slack-sdk/mod.ts";
import handler from "./create-version-pr.ts";
import notify from "./notify-version-pr.ts";
import { VersionPrWorkflow } from "../manifest.ts";
import trigger from "../triggers/create-version-pr.ts";

const { createContext } = SlackFunctionTester("sdg_create_version_pr");
const { createContext: notifyContext } = SlackFunctionTester(
  "sdg_version_pr_notify",
);
// SDK 2.15.1은 런타임의 뷰 핸들러 메서드를 타입에 노출하지 않습니다.
const interactive = handler as typeof handler & {
  viewSubmission(context: unknown): Promise<{ response_action?: string }>;
  viewClosed(context: unknown): Promise<void>;
};
const inputs = {
  channel_id: "C093TU3CCBG",
  user_id: "UTEST",
  interactivity: {
    interactivity_pointer: "test-pointer",
    interactor: { id: "UTEST", secret: "test-secret" },
  },
};
const token = "github_pat_TEST_NOT_A_REAL_TOKEN";

Deno.test("개인 입력창의 토큰으로 실행 요청하고 소유자를 담당자로 전달하며 토큰은 남기지 않음", async () => {
  assert.equal(trigger.inputs?.user_id?.value, "{{data.user_id}}");
  assert.equal(trigger.inputs?.interactivity?.value, "{{data.interactivity}}");
  const steps = VersionPrWorkflow.export().steps;
  assert.equal(steps[0].function_id, "#/functions/sdg_create_version_pr");
  assert.equal(steps[1].function_id, "#/functions/sdg_version_pr_notify");
  assert.equal(steps[1].inputs.message, "{{steps.0.message}}");
  const originalFetch = globalThis.fetch;
  let openOK = true;
  let notificationOK = true;
  let completeOK = true;
  let profileStatus = 200;
  let dispatchStatus = 204;
  let failure = "";
  let profile: Record<string, unknown> = {
    id: 37494776,
    login: "Songgyubin",
    type: "User",
  };
  let profiles = 0;
  let requests = 0;
  let notifications = 0;
  const outputs: { message: string }[] = [];
  globalThis.fetch = (url, init) => {
    const target = String(url);
    if (target === "https://slack.com/api/views.open") {
      const body = new URLSearchParams(String(init?.body));
      assert.equal(body.get("interactivity_pointer"), "test-pointer");
      const view = JSON.parse(body.get("view") || "{}");
      assert.equal(view.callback_id, "sdg_version_pr_token");
      assert.equal(view.notify_on_close, true);
      assert.match(
        view.blocks[0].text.text,
        /target_name=shopl.*actions=write/,
      );
      assert.match(
        view.blocks[0].text.text,
        /저장하지 않으며 실행할 때마다 입력/,
      );
      assert.equal(view.blocks[1].block_id, "github_token");
      assert.equal(view.blocks[1].element.action_id, "token");
      assert.equal(view.private_metadata, undefined);
      return Promise.resolve(Response.json({ ok: openOK }));
    }
    if (target === "https://slack.com/api/functions.completeSuccess") {
      const body = new URLSearchParams(String(init?.body));
      assert.equal(body.get("function_execution_id"), "FxTEST");
      const result = JSON.parse(body.get("outputs") || "{}");
      assert.deepEqual(Object.keys(result), ["message"]);
      assert.ok(!JSON.stringify(result).includes(token));
      outputs.push(result);
      return Promise.resolve(Response.json({ ok: completeOK }));
    }
    if (target === "https://slack.com/api/chat.postMessage") {
      notifications++;
      const body = new URLSearchParams(String(init?.body));
      assert.equal(body.get("channel"), "C093TU3CCBG");
      assert.match(body.get("text") || "", /<@UTEST>.*Songgyubin/);
      return Promise.resolve(Response.json({ ok: notificationOK }));
    }
    assert.equal(
      new Headers(init?.headers).get("Authorization"),
      `Bearer ${token}`,
    );
    assert.equal(init?.redirect, "error");
    if (target === "https://api.github.com/user") {
      profiles++;
      assert.equal(init?.method || "GET", "GET");
      if (failure === "profile") {
        return Promise.reject(new Error(`Network error ${token}`));
      }
      return Promise.resolve(Response.json(profile, { status: profileStatus }));
    }
    assert.equal(
      target,
      "https://api.github.com/repos/shopl/shopl-design-guide-android/actions/workflows/create-version-pr.yml/dispatches",
    );
    requests++;
    assert.equal(init?.method, "POST");
    assert.deepEqual(JSON.parse(String(init?.body)), {
      ref: "main",
      inputs: { requester_slack_id: "UTEST", assignee_id: "37494776" },
    });
    assert.ok(!String(init?.body).includes(token));
    if (failure === "dispatch") {
      return Promise.reject(new Error(`Network error ${token}`));
    }
    return Promise.resolve(new Response(null, { status: dispatchStatus }));
  };
  const start = (overrides = {}) =>
    handler(createContext({
      team_id: "T016H73FHDH",
      inputs,
      env: { SDG_GITHUB_ACTIONS_TOKEN: "DO_NOT_USE_SHARED_TOKEN" },
      ...overrides,
    }));
  const context = (value = token, user = "UTEST", team = "T016H73FHDH") => ({
    ...createContext({ team_id: team, inputs }),
    body: {
      type: "view_submission",
      user: { id: user },
      function_data: { execution_id: "FxTEST" },
    },
    view: {
      callback_id: "sdg_version_pr_token",
      state: { values: { github_token: { token: { value } } } },
    },
  });
  const submit = (value = token, user = "UTEST", team = "T016H73FHDH") =>
    interactive.viewSubmission(context(value, user, team));
  const rejected = async (
    value = token,
    user = "UTEST",
    team = "T016H73FHDH",
  ) => {
    const result = await submit(value, user, team);
    assert.equal(result?.response_action, "errors");
    const text = JSON.stringify(result);
    assert.ok(!text.includes(token));
    return text;
  };
  const send = (
    message: string,
    channel = "C093TU3CCBG",
    team = "T016H73FHDH",
  ) =>
    notify(notifyContext({
      inputs: { channel_id: channel, message },
      team_id: team,
    }));
  try {
    assert.match((await start({ team_id: "TOTHER" })).error || "", /채널/);
    assert.match(
      (await start({ inputs: { ...inputs, channel_id: "C082MGJUVHR" } }))
        .error || "",
      /채널/,
    );
    assert.match(
      (await start({ inputs: { ...inputs, user_id: "UOTHER" } })).error || "",
      /Slack 사용자/,
    );
    openOK = false;
    assert.match((await start()).error || "", /입력창/);
    openOK = true;
    assert.equal((await start()).completed, false);
    await rejected(token, "UOTHER");
    await rejected(token, "UTEST", "TOTHER");
    for (
      const value of [
        "",
        "gho_OAUTH",
        "github_pat_bad\nheader",
        "github_pat_" + "A".repeat(256),
      ]
    ) await rejected(value);
    assert.equal(profiles, 0);
    profileStatus = 401;
    assert.match(await rejected(), /HTTP 401/);
    profileStatus = 200;
    const valid = { ...profile };
    for (
      const invalid of [{ id: "37494776" }, { id: 0 }, {
        login: "bad<@UOTHER>",
      }, { type: "Organization" }]
    ) {
      profile = { ...valid, ...invalid };
      assert.match(await rejected(), /개인 계정/);
    }
    profile = valid;
    failure = "profile";
    assert.match(await rejected(), /계정을 확인하지 못/);
    failure = "";
    assert.equal(requests, 0);
    assert.equal(outputs.length, 0);
    for (const status of [403, 404]) {
      dispatchStatus = status;
      assert.match(await rejected(), new RegExp(`HTTP ${status}`));
    }
    assert.equal(outputs.length, 0);
    assert.equal(notifications, 0);
    dispatchStatus = 204;
    failure = "dispatch";
    assert.match(await rejected(), /접수 여부/);
    failure = "";
    completeOK = false;
    assert.match(await rejected(), /요청은 접수됐지만/);
    completeOK = true;
    dispatchStatus = 200;
    assert.deepEqual(await submit(), { response_action: "clear" });
    dispatchStatus = 204;
    assert.deepEqual(await submit(`  ${token}  `), {
      response_action: "clear",
    });
    assert.match(outputs.at(-1)?.message || "", /<@UTEST>.*Songgyubin/);
    assert.deepEqual((await send(outputs.at(-1)!.message)).outputs, {});
    assert.equal(notifications, 1);
    notificationOK = false;
    assert.match(
      (await send(outputs.at(-1)!.message)).error || "",
      /알림을 보내지 못/,
    );
    const closed = {
      ...context(),
      body: { ...context().body, type: "view_closed" },
    };
    const count = requests;
    await interactive.viewClosed(closed);
    assert.equal(requests, count);
    assert.deepEqual(outputs.at(-1), { message: "" });
    const sentCount = notifications;
    await send("");
    assert.equal(notifications, sentCount);
    assert.match((await send("", "COTHER")).error || "", /채널/);
    assert.match((await send("", "C093TU3CCBG", "TOTHER")).error || "", /채널/);
    const completedCount = outputs.length;
    await interactive.viewClosed({
      ...closed,
      body: { ...closed.body, user: { id: "UOTHER" } },
    });
    assert.equal(outputs.length, completedCount);
  } finally {
    globalThis.fetch = originalFetch;
  }
});
