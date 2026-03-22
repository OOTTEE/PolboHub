import {MultiServerMCPClient} from "@langchain/mcp-adapters";
import {agentConfig} from "../config/server.config";
import {createMcpClient} from "./client";

const POSTGRES_SERVER_COMMAND = 'npx';
const POSTGRES_SERVER_ARGS = ['-y', '@modelcontextprotocol/server-postgres'];

export function createPostgresMcpClient(): MultiServerMCPClient {

    return createMcpClient({
            'postgresPolbohub': {
                command: POSTGRES_SERVER_COMMAND,
                args: POSTGRES_SERVER_ARGS,
                databaseUrl: agentConfig.mcpTools?.databasePolbohubUrl
            },
            'postgresScrapper': {
                command: POSTGRES_SERVER_COMMAND,
                args: POSTGRES_SERVER_ARGS,
                databaseUrl: agentConfig.mcpTools?.databaseScrapperUrl
            }
        },
    )


}