import {createDeepAgent, LocalShellBackend} from "deepagents";
import {SYSTEM_PROMPT} from "./promt/SystemPromt";
import {agentConfig} from "./config/server.config";
import {createPostgresMcpClient} from "./mcp/mcpPostgresClients";
import {ChatGoogle} from "@langchain/google";
import {BaseMessage, HumanMessage} from "@langchain/core/messages";
import * as readline from "node:readline/promises";
import * as path from 'node:path';

export class PolboAgent {

    private agent!: any;

    constructor() {
    }

    public static async init(): Promise<PolboAgent> {
        const instance = new PolboAgent();

        console.log('Init PolboAgent');

        const tools = await createPostgresMcpClient()
            .getTools();

        const llm: ChatGoogle = new ChatGoogle(
            agentConfig.googleModel,
            {
                temperature: agentConfig.googleTemperature,
                apiKey: agentConfig.googleApiKey,
                maxRetries: 2
            }
        );

        const config = {
            model: llm,
            tools: [...tools],
            systemPrompt: SYSTEM_PROMPT,
            skills: ['/skills/'],
            backend: new LocalShellBackend({
                rootDir: path.resolve('.'),
                virtualMode: true
            })
        }

        instance.agent = createDeepAgent(config);

        return instance;
    }

    async startChat(): Promise<void> {
        const rl = readline.createInterface({
            input: process.stdin,
            output: process.stdout
        });

        let messages: BaseMessage[] = [];
        console.log("\n✨ Polbo Agent CLI: Conectado. (Escribe 'salir' para terminar) \n");

        while (true) {
            try {
                const userInput = await rl.question("👤 Tú: ");

                if (userInput.toLowerCase() === "salir" || userInput.toLowerCase() === "exit") {
                    console.log("👋 ¡Adiós!");
                    rl.close();
                    break;
                }

                messages.push(new HumanMessage(userInput));

                process.stdout.write("🤖 Agente: ");

                const response = await this.agent.invoke({messages});

                if (response.messages && response.messages.length > 0) {
                    const lastMessage = response.messages[response.messages.length - 1];
                    if (lastMessage) {
                        console.log(lastMessage.content);
                    }
                    messages = response.messages;
                }
            } catch (error) {
                console.error("\n❌ Error en el agente:", error);
            }

            console.log("\n" + "─".repeat(40) + "\n");
        }
    }
}
