import { Page } from 'playwright';
import { browserService } from '../browser/browser.service';
import { db } from '../../db';
import { marksEntity } from '../../db/schema/schema';
import { Logger } from '../logger/logger.service';
import { serverConfig } from '../../config/server.config';
import {logger} from "../../config/logger.config";

export class FeganScrapperService {
    private static instance: FeganScrapperService;
    public static baseUrl = serverConfig.services.fegan.urlPage;
    
    public logger = new Logger(this.constructor.name);

    public static getInstance() {
        if (!this.instance) {
            this.instance = new FeganScrapperService();
        }
        return this.instance;
    }

    public async scrapMarks() : Promise<void> {
        const page: Page = await browserService.getPage();

        this.logger.debug(`Navigating to Fegan marks page ${FeganScrapperService.baseUrl}`);
        await page.goto(FeganScrapperService.baseUrl);
        await page.waitForLoadState('networkidle' );

        const data = {
            'combo_estament': 'MASTE', // DEPOR => Depor , MASTE => Master
            'combo_event': '50B',
            'input_from_date': '2025-01-01',
            'input_to_date': '2026-01-15',
            // 'combo_gender': 'M', // F => femenino, M => masculino, X = mixto,
            // // 'combo_split': 'N', // S , N
            // 'input_from_year': '2025',
            // 'input_to_year': '2026',
            // 'combo_pool': '25',
            'combo_crono': 'M', // M => manual, E => electronico
            'input_license': '1143890',
            // 'input_name': 'martiño',
            // 'combo_club': '01547', // 01547 => ESCUALOS
            // 'combo_show_first': '100',
            // 'input_best_mark': 'on',
            // 'input_equate': 'on'
        };

        await this.fillQueryForm(page, data);

        await this.sendForm(page);

        await page.waitForLoadState('networkidle' );

        await this.readTableResults(page);

        await page.close();
    }

    private async fillQueryForm(page: Page, data: Record<string, string>) : Promise<void> {
        this.logger.debug("Filling query form");

        await page.evaluate((formData) => {
            for (const [key, value] of Object.entries(formData)) {
                const element = document.getElementsByName(key)[0] as HTMLInputElement | HTMLSelectElement;
                if (element) {
                    element.value = value;
                }
            }
        }, data);
    }

    private async sendForm(page: Page) : Promise<void> {
        logger.debug("Sending query form");
        await Promise.all([
            page.waitForLoadState('networkidle' ),
            page.locator("[type=submit]").click()
        ])
    }

    private async readTableResults(page: Page) : Promise<void> {
        logger.debug("Reading table results");
        const tableResults = await page.evaluate(() => {
            const container = document.getElementById('dvData') ?? document;
            const rows = Array.from(container.querySelectorAll('table tbody tr'));
            const data = rows.map((row) => {
                const cells = Array.from(row.querySelectorAll('td')).map((td) => (td.textContent || '').trim());
                if (cells.length < 10) return null;
                return {
                    licence: cells[0],
                    name: cells[1],
                    year: parseInt(cells[2] || ''),
                    club: cells[3],
                    event: cells[4],
                    mark: cells[5],
                    cp: cells[6],
                    date: cells[7],
                    place: cells[8],
                    partial: cells[9]
                };
            }).filter(Boolean) as Array<{licence:string,name:string,year:number,club:string,event:string,mark:string,cp:string,date:string,place:string,partial:string}>;
            return data;
        });

        this.logger.debug(`Parsed ${tableResults.length} rows from results table`);

        for (const row of tableResults) {
            const toInsert = {
                licence: row.licence,
                name: row.name,
                year: isNaN(row.year) ? null : row.year,
                club: row.club,
                event: row.event,
                mark: row.mark,
                cp: row.cp,
                date: this.parseDate(row.date),
                place: row.place,
                partial: (row.partial || '').toUpperCase() === 'S'
            } as any;

            await this.insertMark(toInsert);
        }
    }


    private parseDate(dateStr: string): Date {
        // Expected format YYYYMMDD
        const year = parseInt(dateStr.substring(0, 4));
        const month = parseInt(dateStr.substring(4, 6)) - 1; // 0-indexed
        const day = parseInt(dateStr.substring(6, 8));
        return new Date(year, month, day);
    }

    private async insertMark(data: any): Promise<void> {
        this.logger.debug(`Inserting mark for ${data.name}`);
        try {
            await db.insert(marksEntity).values(data);
        } catch (error) {
            this.logger.error(`Error inserting mark`, error);
        }
    }


}

export const feganScrapperService = FeganScrapperService.getInstance();