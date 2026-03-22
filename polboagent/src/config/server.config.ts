import * as fs from 'fs'
import {join} from 'path'
import dotenv from 'dotenv'

const envPath = join(process.cwd(), '.env')
const envExists = fs.existsSync(envPath)

if (envExists) {
    dotenv.config({ path: envPath })
} else {
    dotenv.config()
}

export interface AgentConfig {
    logLevel: string
    mcpTools: McpTools
    googleApiKey: string
    googleModel: string
    googleTemperature: number
}

export interface McpTools {
    databasePolbohubUrl: string,
    databaseScrapperUrl: string
}

export const agentConfig: AgentConfig = {
    logLevel: process.env.LOG_LEVEL || 'info',
    googleApiKey: process.env.GOOGLE_API_KEY || 'missing-key',
    googleModel: process.env.GOOGLE_MODEL || 'gemini-3.1-flash-lite-preview',
    googleTemperature: parseFloat(process.env.GOOGLE_TEMPERATURE || '0.3'),
    mcpTools: {
        databasePolbohubUrl: process.env.DATABASE_POLBOHUB_URL || '',
        databaseScrapperUrl: process.env.DATABASE_SCRAPPER_URL || ''
    }
}
