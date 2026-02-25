import { Page } from 'playwright';
import { browserService } from '../browser/browser.service';
import { db } from '../../db';
import { marksEntity } from '../../db/schema/schema';
import { Logger } from '../logger/logger.service';
import { serverConfig } from '../../config/server.config';

export class FeganScrapperService {
    private static instance: FeganScrapperService;
    public static baseUrl = serverConfig.services.fegan.urlPage;
    
    public logger: Logger = new Logger(this.constructor.name);

    public static getInstance() {
        if (!this.instance) {
            this.instance = new FeganScrapperService();
        }
        return this.instance;
    }

    public async scrapMarks(request: FeganScrapperRequest) : Promise<void> {
        const page: Page = await browserService.getPage();

        this.logger.debug(`Navigating to Fegan marks page ${FeganScrapperService.baseUrl}`);
        await page.goto(FeganScrapperService.baseUrl);
        await page.waitForLoadState('networkidle' );

        const data = this.build_form_record(request);

        this.logger.debug(`Filling query form with data: ${JSON.stringify(data)}`);

        await this.fillQueryForm(page, data);

        await this.sendForm(page);

        await page.waitForLoadState('networkidle' );

        await this. readTableResults(page);

        await page.close();
    }

    private async fillQueryForm(page: Page, data: Record<string, string>) : Promise<void> {
        this.logger.debug("Filling query form");

        await page.evaluate((formData) => {
            for (const [key, value] of Object.entries(formData)) {
                console.log(key, value);
                const element = document.getElementsByName(key)[0] as HTMLInputElement | HTMLSelectElement;
                console.log(element);
                if (element) {
                    if(key === 'combo_show_first') {
                        element.appendChild(new Option(value, value, false, true));
                    } else {
                        element.value = value;
                    }
                }
            }
        }, data);
    }

    private async sendForm(page: Page) : Promise<void> {
        this.logger.debug("Sending query form");
        await Promise.all([
            page.waitForLoadState('networkidle' ),
            page.locator("[type=submit]").click()
        ])
    }

    private async readTableResults(page: Page) : Promise<void> {
        this.logger.debug("Reading table results");
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
                mark: this.parseMark(row.mark),
                cp: row.cp,
                date: this.parseDate(row.date),
                place: row.place,
                partial: (row.partial || '').toUpperCase() === 'S'
            } as any;

            await this.insertMark(toInsert);
        }
    }

    private async insertMark(data: any): Promise<void> {
        this.logger.trace(`Inserting mark for ${data.name}`);
        try {
            return db.insert(marksEntity).values(data) as any;
        } catch (error) {
            this.logger.error(`Error inserting mark`, error);
        }
    }

    private parseMark(mark: string): string {
        if (!mark) return '0';
        const parts = mark.replace(',', '.').split(':').reverse();
        let totalSeconds = 0;

        // seconds and milliseconds (SS.hh)
        if (parts.length >= 1) {
            totalSeconds += parseFloat(parts[0]);
        }
        // minutes (MM)
        if (parts.length >= 2) {
            totalSeconds += parseInt(parts[1]) * 60;
        }
        // hours (H)
        if (parts.length >= 3) {
            totalSeconds += parseInt(parts[2]) * 3600;
        }

        return totalSeconds.toFixed(2);
    }

    private parseDate(dateStr: string): string {
        // Handle YYYYMMDD or DD/MM/YYYY
        if (dateStr.includes('/')) {
            const [day, month, year] = dateStr.split('/');
            return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
        }

        const year = dateStr.substring(0, 4);
        const month = dateStr.substring(4, 6);
        const day = dateStr.substring(6, 8);
        return `${year}-${month}-${day}`;
    }

    private build_form_record(request: FeganScrapperRequest): Record<string, string> {
        const data: Record<string, string> = {};
        data['combo_estament'] = (request.estament) ? request.estament : '';
        data['combo_event'] = (request.event) ? request.event : '';
        data['input_from_date'] = (request.from_date) ? request.from_date : '';
        data['input_to_date'] = (request.to_date) ? request.to_date : '';
        data['combo_gender'] = (request.gender) ? request.gender : '';
        data['combo_split'] = (request.split) ? request.split : '';
        data['input_from_year'] = (request.from_year) ? request.from_year : '';
        data['input_to_year'] = (request.to_year) ? request.to_year : '';
        data['combo_pool'] = (request.pool) ? request.pool : '';
        data['combo_crono'] = (request.crono) ? request.crono : '';
        data['input_license'] = (request.license) ? request.license : '';
        data['input_name'] = (request.name) ? request.name : '';
        data['combo_club'] = (request.club) ? request.club : '';
        data['combo_show_first'] = (request.show_first) ? request.show_first : '';
        data['input_best_mark'] = (request.best_mark) ? request.best_mark : '';
        data['input_equate'] = (request.equate) ? request.equate : '';
        return data;
}

}

export interface FeganScrapperRequest {
    estament?: EstamentCode;
    event?: EventCode;
    from_date?: string;
    to_date?: string;
    gender?: GenderCode;
    split?: PartialCode;
    from_year?: string;
    to_year?: string;
    pool?: PoolCode;
    crono?: CronoCode;
    license?: string;
    name?: string;
    club?: ClubCode;
    show_first?: string;
    best_mark?: string;
    equate?: string;
}

export const MAPPINGS = {
    ESTAMENT: {
        DEPOR: "Depor",
        MASTE: "Master"
    },

    GENDER: {
        F: "Feminino",
        M: "Masculino",
        X: "Mixto"
    },

    POOL: {
        "25": "25",
        "50": "50"
    },

    CLUBS: {
        "P1101": "ACUATICA",
        "01464": "ADAS",
        "00617": "ADFOGAR",
        "00719": "ALBATRO",
        "01564": "ARTABRIA",
        "01133": "ARTEIXO",
        "P1112": "ARZUA",
        "01837": "ASMEIGAS",
        "00744": "ASPONTE",
        "00818": "BAIMIÑO",
        "P1106": "BOIRO",
        "00770": "CEDEIRA",
        "00471": "CERCEDA",
        "01295": "CIDSANTI",
        "00313": "CLMVIGA",
        "01244": "CNAFENE",
        "P1103": "CNCERVO",
        "00855": "CNLALIN",
        "P1105": "CNMARIN",
        "01423": "CNSALNES",
        "00511": "CNSANTI",
        "00852": "CNUMIA",
        "01146": "CNVIGO",
        "P1107": "CNVILAG",
        "00445": "CODESAL",
        "01773": "CORSAL",
        "01433": "CULLERED",
        "00282": "DELMAR",
        "01397": "ENLALIN",
        "00310": "ENMILIT",
        "00832": "ESBETAN",
        "01547": "ESCUALOS",
        "F0111": "FEGAN",
        "00398": "FLUVLUGO",
        "00784": "GALAICO",
        "01247": "GALAICOP",
        "00506": "GOLFIÑOS",
        "01572": "HERCULES",
        "01853": "IHAVEADR",
        "01269": "LICEO",
        "00146": "MARINAFE",
        "01294": "MASTMOS",
        "01436": "MIRAFLOR",
        "00837": "MOLEMOS",
        "01424": "MOS",
        "01848": "MTRINID",
        "01824": "MULLEREF",
        "00186": "NCORUÑA",
        "05032": "NDALIMIA",
        "00674": "NFERROL",
        "01465": "NOLEIROS",
        "00816": "NPADRON",
        "00314": "NPONTEV",
        "00639": "NTNARON",
        "01438": "OLIVEDRA",
        "01766": "ORDES",
        "01785": "PANAS",
        "00819": "PONSVETU",
        "00878": "PONTEARE",
        "01156": "PORTMIÑA",
        "00101": "POURENSE",
        "00765": "RBAIXAS",
        "00071": "RCNVIGO",
        "00873": "REDONDEL",
        "00769": "RIVEIRA",
        "00284": "RSDH",
        "01576": "SALESTRA",
        "01659": "SALVEURO",
        "01422": "SANXENXO",
        "00287": "SCASINO",
        "S0001": "SELNOR",
        "S0002": "SELSUR",
        "01116": "SFERROL",
        "01242": "SOURENSE",
        "01663": "STACOMBA",
        "00824": "TRAVIESA",
        "01762": "TRICORUÑ",
        "01178": "USANTIAG",
        "00126": "USC",
        "01688": "VALDEORR",
        "00622": "XOVE"
    },

    EVENTS: {
        "50L": "50L",
        "100L": "100L",
        "200L": "200L",
        "400L": "400L",
        "800L": "800L",
        "1500L": "1500L",
        "50M": "50M",
        "100M": "100M",
        "200M": "200M",
        "50E": "50E",
        "100E": "100E",
        "200E": "200E",
        "50B": "50B",
        "100B": "100B",
        "200B": "200B",
        "100S": "100S",
        "200S": "200S",
        "400S": "400S",
        "4x50L": "4x50L",
        "4x100L": "4x100L",
        "4x200L": "4x200L",
        "4x50S": "4x50S",
        "4x100S": "4x100S",
        "2000L": "2000L",
        "3000L": "3000L",
        "5000L": "5000L"
    },

    PARTIAL: {
        S: "SI",
        N: "NO"
    },

    CHRONO: {
        M: "Manual",
        E: "Electrónico"
    },

} as const;

export type ClubCode = keyof typeof MAPPINGS.CLUBS;
export type EventCode = keyof typeof MAPPINGS.EVENTS;
export type GenderCode = keyof typeof MAPPINGS.GENDER;
export type EstamentCode = keyof typeof MAPPINGS.ESTAMENT;
export type CronoCode = keyof typeof MAPPINGS.CHRONO;
export type PoolCode = keyof typeof MAPPINGS.POOL;
export type PartialCode = keyof typeof MAPPINGS.PARTIAL;

export class MappingMappers {
    static getStatementName(estamentCode: EstamentCode): string {
        return MAPPINGS.ESTAMENT[estamentCode as EstamentCode] || '';
    }

    static getClubName(clubCode: ClubCode): string {
        return MAPPINGS.CLUBS[clubCode as ClubCode] || '';
    }

    static getEventName(eventCode: EventCode): string {
        return MAPPINGS.EVENTS[eventCode as EventCode] || '';
    }

    static getGenderName(genderCode: GenderCode): string {
        return MAPPINGS.GENDER[genderCode as GenderCode] || '';
    }

    static getPartialName(partialCode: PartialCode): string {
        return MAPPINGS.PARTIAL[partialCode as PartialCode] || '';
    }

    static getChronoName(chronoCode: CronoCode): string {
        return MAPPINGS.CHRONO[chronoCode as CronoCode] || '';
    }

    static getPoolName(poolCode: PoolCode): string {
        return MAPPINGS.POOL[poolCode as PoolCode] || '';
    }
}


export const feganScrapperService = FeganScrapperService.getInstance();