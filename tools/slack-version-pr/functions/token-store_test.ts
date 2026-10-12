import assert from "node:assert/strict";
import { SlackFunctionTester } from "deno-slack-sdk/mod.ts";
import {
  decryptToken,
  deleteToken,
  encryptToken,
  loadToken,
  saveToken,
} from "./token-store.ts";

const key = btoa(String.fromCharCode(...new Uint8Array(32).fill(7)));
const token = "github_pat_NOT_A_REAL_TOKEN";
const id = "T016H73FHDH:UTEST";

Deno.test("AES-GCM 암호문만 저장하고 사용자·계정·키·변조를 검증", async () => {
  const encrypted_token = await encryptToken(token, key, id, 123);
  const record = {
    id,
    github_id: 123,
    github_login: "test-user",
    encrypted_token,
  };
  assert.ok(!encrypted_token.includes(token));
  assert.notEqual(encrypted_token, await encryptToken(token, key, id, 123));
  assert.equal(await decryptToken(record, key), token);
  const otherKey = btoa(String.fromCharCode(...new Uint8Array(32).fill(8)));
  for (
    const changed of [
      { ...record, id: "T016H73FHDH:UOTHER" },
      { ...record, id: "TOTHER:UTEST" },
      { ...record, github_id: 456 },
      { ...record, encrypted_token: encrypted_token.slice(0, -5) + "AAAAA" },
      { ...record, encrypted_token: "v2.invalid.invalid" },
    ]
  ) await assert.rejects(decryptToken(changed, key), /복호화/);
  await assert.rejects(decryptToken(record, otherKey), /복호화/);
  for (const invalid of ["", "bad", btoa("short")]) {
    await assert.rejects(
      encryptToken(token, invalid, id, 123),
      /암호화 키 설정/,
    );
  }

  const originalFetch = globalThis.fetch;
  const { createContext } = SlackFunctionTester("sdg_create_version_pr");
  const { client } = createContext({ inputs: {} });
  let item: Record<string, unknown> = {};
  let ok = true;
  let requests = 0;
  globalThis.fetch = (url, init) => {
    requests++;
    const body = new URLSearchParams(String(init?.body));
    assert.equal(body.get("datastore"), "sdg_github_tokens");
    if (String(url).endsWith(".put")) {
      item = JSON.parse(body.get("item") || "{}");
      assert.ok(!JSON.stringify(item).includes(token));
      assert.equal(item.id, id);
    } else {
      assert.equal(body.get("id"), id);
      if (String(url).endsWith(".delete") && ok) item = {};
      else {assert.ok(
          String(url).endsWith(".get") || String(url).endsWith(".delete"),
        );}
    }
    return Promise.resolve(Response.json({ ok, item, error: token }));
  };
  try {
    assert.equal(await loadToken(client, "T016H73FHDH", "UTEST"), null);
    await saveToken(client, key, "T016H73FHDH", "UTEST", token, {
      id: 123,
      login: "test-user",
    });
    const loaded = await loadToken(client, "T016H73FHDH", "UTEST");
    assert.equal(await decryptToken(loaded!, key), token);
    const count = requests;
    for (
      const [team, user] of [["TOTHER", "UTEST"], ["T016H73FHDH", "<@UOTHER>"]]
    ) {
      await assert.rejects(loadToken(client, team, user), /Slack 사용자/);
      await assert.rejects(deleteToken(client, team, user), /Slack 사용자/);
      await assert.rejects(
        saveToken(client, key, team, user, token, {
          id: 123,
          login: "test-user",
        }),
        /Slack 사용자/,
      );
    }
    assert.equal(requests, count);
    item.id = "T016H73FHDH:UOTHER";
    await assert.rejects(
      loadToken(client, "T016H73FHDH", "UTEST"),
      /계정 정보/,
    );
    item.id = id;
    ok = false;
    await assert.rejects(loadToken(client, "T016H73FHDH", "UTEST"), /조회/);
    await assert.rejects(deleteToken(client, "T016H73FHDH", "UTEST"), /삭제/);
    await assert.rejects(
      saveToken(client, key, "T016H73FHDH", "UTEST", token, {
        id: 123,
        login: "test-user",
      }),
      /저장/,
    );
    ok = true;
    await deleteToken(client, "T016H73FHDH", "UTEST");
    assert.equal(await loadToken(client, "T016H73FHDH", "UTEST"), null);
  } finally {
    globalThis.fetch = originalFetch;
  }
});
