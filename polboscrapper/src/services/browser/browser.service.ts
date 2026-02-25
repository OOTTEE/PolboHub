import { chromium, Browser, Page } from 'playwright';
import { Logger } from '../logger/logger.service';

export class BrowserService {
    public static instance: BrowserService;

    public logger: Logger = new Logger('browser')

    public browser: Browser | null = null;
    public initialized: boolean = false;

    public static getInstance() {
        if (!this.instance) {
            this.instance = new BrowserService();
        }
        return this.instance;
    }

    private constructor() {
        this.init()
    }

    public async init() : Promise<void> {
        try {
            this.logger.debug('Initializing headless browser');
            this.logger.debug('Using Chromium channel: chromium');
            this.browser = await chromium.launch({
                headless: false,
                // channel: 'chromium',
                chromiumSandbox: false
            });
            this.initialized = true;
            this.logger.debug('Browser initialized');
        } catch (error: unknown) {
            this.logger.error('Failed browser initialization', error);
            await this.close();
            throw new Error('Failed browser initialization', { cause: error });
        }
    }

    public async getPage() : Promise<Page> {
        if (!this.initialized || !this.browser) {
            throw new Error('Browser not initialized');
        }
        return await this.browser.newPage();
    }

    public async close() : Promise<void> {
        if (this.browser) {
            this.logger.debug('Closing browser');
            this.browser.close()
            this.browser = null;
            this.initialized = false;
            this.logger.debug('Browser closed');
        }
    }

}

export const browserService = BrowserService.getInstance();