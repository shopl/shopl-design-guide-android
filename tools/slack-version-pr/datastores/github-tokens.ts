import { DefineDatastore, Schema } from "deno-slack-sdk/mod.ts";

export default DefineDatastore({
  name: "sdg_github_tokens",
  primary_key: "id",
  attributes: {
    id: { type: Schema.types.string },
    github_id: { type: Schema.types.number },
    github_login: { type: Schema.types.string },
    branch_author: { type: Schema.types.string },
    encrypted_token: { type: Schema.types.string },
  },
});
