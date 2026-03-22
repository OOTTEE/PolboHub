import {PolboAgent} from "./PolboAgent"

async function main(): Promise<void> {
    console.log('initializing PolboAgent.')

    const agent = await PolboAgent.init();
    await agent.startChat();

}

main().catch(console.error);