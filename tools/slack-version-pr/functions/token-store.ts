import type { SlackAPI } from "deno-slack-api/mod.ts";
import GitHubTokens from "../datastores/github-tokens.ts";

type Client = ReturnType<typeof SlackAPI>;
type Owner = { id: number; login: string };
export type TokenRecord = {
  id: string;
  github_id: number;
  github_login: string;
  branch_author?: string;
  encrypted_token: string;
};
export const isBranchAuthor = (value: unknown): value is string =>
  typeof value === "string" && /^[a-z][a-z0-9-]{0,31}$/.test(value);
const encode = (bytes: Uint8Array) => btoa(String.fromCharCode(...bytes));
const decode = (value: string) =>
  Uint8Array.from(atob(value), (c) => c.charCodeAt(0));
const recordId = (team: string, user: string) => {
  if (team !== "T016H73FHDH" || !/^[UW][A-Z0-9]+$/.test(user)) {
    throw new Error("개인 토큰의 Slack 사용자를 확인해주세요.");
  }
  return `${team}:${user}`;
};

async function encryptionKey(value: string) {
  try {
    const bytes = decode(value);
    if (bytes.length !== 32) throw new Error();
    return await crypto.subtle.importKey("raw", bytes, "AES-GCM", false, [
      "encrypt",
      "decrypt",
    ]);
  } catch {
    throw new Error(
      "SDG_TOKEN_ENCRYPTION_KEY의 32바이트 암호화 키 설정을 확인해주세요.",
    );
  }
}

export async function encryptToken(
  token: string,
  key: string,
  id: string,
  githubId: number,
) {
  const imported = await encryptionKey(key);
  const iv = crypto.getRandomValues(new Uint8Array(12));
  const encrypted = await crypto.subtle.encrypt(
    {
      name: "AES-GCM",
      iv,
      additionalData: new TextEncoder().encode(
        `${GitHubTokens.name}:${id}:${githubId}`,
      ),
    },
    imported,
    new TextEncoder().encode(token),
  );
  return `v1.${encode(iv)}.${encode(new Uint8Array(encrypted))}`;
}

export async function decryptToken(record: TokenRecord, key: string) {
  const imported = await encryptionKey(key);
  try {
    const [version, iv, encrypted, extra] = record.encrypted_token.split(".");
    if (version !== "v1" || extra !== undefined || decode(iv).length !== 12) {
      throw new Error();
    }
    const decrypted = await crypto.subtle.decrypt(
      {
        name: "AES-GCM",
        iv: decode(iv),
        additionalData: new TextEncoder().encode(
          `${GitHubTokens.name}:${record.id}:${record.github_id}`,
        ),
      },
      imported,
      decode(encrypted),
    );
    return new TextDecoder().decode(decrypted);
  } catch {
    throw new Error(
      "저장된 토큰을 복호화하지 못했습니다. 암호화 키를 확인하거나 토큰을 다시 등록해주세요.",
    );
  }
}

export async function loadToken(
  client: Client,
  team: string,
  user: string,
): Promise<TokenRecord | null> {
  const id = recordId(team, user);
  const saved = await client.apps.datastore.get<typeof GitHubTokens.definition>(
    { datastore: GitHubTokens.name, id },
  );
  if (!saved.ok) {
    throw new Error("저장된 개인 토큰 정보를 조회하지 못했습니다.");
  }
  if (!saved.item || Object.keys(saved.item).length === 0) return null;
  if (
    saved.item.id !== id || !Number.isSafeInteger(saved.item.github_id) ||
    saved.item.github_id! < 1 ||
    typeof saved.item.github_login !== "string" ||
    !/^[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?$/.test(
      saved.item.github_login,
    ) ||
    typeof saved.item.encrypted_token !== "string" ||
    (saved.item.branch_author !== undefined &&
      !isBranchAuthor(saved.item.branch_author))
  ) {
    throw new Error("저장된 개인 토큰의 계정 정보를 확인해주세요.");
  }
  return saved.item as TokenRecord;
}

export async function saveToken(
  client: Client,
  key: string,
  team: string,
  user: string,
  token: string,
  owner: Owner,
  branchAuthor: string,
) {
  if (!isBranchAuthor(branchAuthor)) {
    throw new Error("브랜치 작성자 이름을 확인해주세요.");
  }
  const id = recordId(team, user);
  const encrypted_token = await encryptToken(token, key, id, owner.id);
  const saved = await client.apps.datastore.put<typeof GitHubTokens.definition>(
    {
      datastore: GitHubTokens.name,
      item: {
        id,
        github_id: owner.id,
        github_login: owner.login,
        branch_author: branchAuthor,
        encrypted_token,
      },
    },
  );
  if (!saved.ok) {
    throw new Error(
      "개인 토큰을 암호화해 저장하지 못했습니다. 실행을 요청하지 않았습니다.",
    );
  }
}

export async function deleteToken(client: Client, team: string, user: string) {
  const deleted = await client.apps.datastore.delete({
    datastore: GitHubTokens.name,
    id: recordId(team, user),
  });
  if (!deleted.ok) throw new Error("저장된 개인 토큰을 삭제하지 못했습니다.");
}
