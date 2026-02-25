import { logger } from '../../config/logger.config'

export class Logger {

    private context: string;

    constructor(context: string) {
        this.context = context
    }

    info(message: string, metadata?: Record<string, any>) {
        logger.info({...metadata, context: this.context}, message)
    }

    warn(message: string, metadata?: Record<string, any>) {
        logger.warn({...metadata, context: this.context}, message)
    }

    debug(message: string, metadata?: Record<string, any>) {
        logger.debug({...metadata, context: this.context}, message)
    }

    trace(message: string, metadata?: Record<string, any>) {
        logger.trace({...metadata, context: this.context}, message)
    }

    error(message: string, error?: unknown, metadata?: Record<string, any>) {
        if (error instanceof Error) {
            logger.error({...metadata, context: this.context, message: error.message, stack: error.stack}, message);
        } else {
            logger.error({...metadata, context: this.context, message: String(error)}, message);
        }
    }
}
