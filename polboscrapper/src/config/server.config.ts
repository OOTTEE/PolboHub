import * as fs from 'fs'
import { join } from 'path'
import dotenv from 'dotenv'

const envPath = join(process.cwd(), '.env')
const envExists = fs.existsSync(envPath)

if (envExists) {
    dotenv.config({ path: envPath })
} else {
    dotenv.config()
}

export interface ServerConfig {
    port: number
    host: string
    nodeEnv: string
    logLevel: string
    databaseUrl: string | undefined
    services: Record<string, Record<string, string>>
}

export const serverConfig: ServerConfig = {
    port: parseInt(process.env.PORT || '3000', 10),
    host: process.env.HOST || 'localhost',
    nodeEnv: process.env.NODE_ENV || 'production',
    logLevel: process.env.LOG_LEVEL || 'info',
    databaseUrl: process.env.DATABASE_URL,
    services: {
        'fegan': {
            'urlPage': process.env.FEGAN_BASE_URL || ''
        }
    }
}

export default serverConfig;

