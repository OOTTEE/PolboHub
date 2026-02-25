import pino from 'pino'
import { serverConfig } from './server.config'

const pinoConfig: pino.LoggerOptions = {
    level: serverConfig.logLevel,
    ...(serverConfig.nodeEnv === 'development' &&  {
        transport: {
            target: 'pino-pretty',
            options: {
                colorize: true,
                translateTime: 'SYS:standard',
                ignore: 'pid,hostname'
            }
        }
    })
}

export const logger = pino(pinoConfig);