import { McpServer } from '@modelcontextprotocol/server';
import { serveStdio } from '@modelcontextprotocol/server/stdio';
import { readFileSync } from 'node:fs';
import { loadCatalogs } from './catalog.js';
import { registerTools } from './tools.js';
import { serverInstructions } from './usage-policy.js';

// The distributable bundle has no parent package.json. Development builds retain the package fallback.
declare const __SDG_MCP_VERSION__: string;
const version = typeof __SDG_MCP_VERSION__ === 'string' ? __SDG_MCP_VERSION__
  : JSON.parse(readFileSync(new URL('../package.json', import.meta.url), 'utf8')).version;
loadCatalogs();
serveStdio(() => {
  const server = new McpServer({ name: 'sdg-android', version }, {
    instructions: serverInstructions,
  });
  registerTools(server);
  return server;
}, { onerror: error => console.error(error.message) });
