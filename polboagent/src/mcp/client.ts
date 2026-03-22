import {type Connection, MultiServerMCPClient} from "@langchain/mcp-adapters";


export interface McpClientConfig {
    command: string;
    args: string[];
    databaseUrl: string | undefined;
}

export function createMcpClient(config: Record<string, McpClientConfig>): MultiServerMCPClient {

    const configuration: Record<string, Connection> = {};

    Object.entries(config).forEach(([name, conn]) => {

        if (!conn.databaseUrl) {
            throw new Error(`Server database url is not defined for ${name}`);
        }

        if (!conn.command) {
            throw new Error(`Server command is not defined for ${name}`);
        }

        configuration[name] = {
            transport: 'stdio',
            command: conn.command,
            args: [...conn.args, conn.databaseUrl]
        }
    })

    return new MultiServerMCPClient({
        prefixToolNameWithServerName: true,
        mcpServers: configuration
    });

}
