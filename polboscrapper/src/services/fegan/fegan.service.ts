import {
    EventCode,
    feganScrapperService,
    GenderCode,
    MAPPINGS
} from "./feganScrapper.service";
import {db} from "../../db";
import {marksEntity} from "../../db/schema/schema";


export class FeganService {

    public async scrapMarksFromYear(year: number) : Promise<void> {

        const events = Object.keys(MAPPINGS.EVENTS) as EventCode[];
        const genders: GenderCode[] = ['M', 'F'];

        for (const event of events) {
            for (const gender of genders) {
                console.log(`Scrapping marks for year ${year}, event ${event}, gender ${gender}`);
                await feganScrapperService.scrapMarks({
                    from_date: `${year}-01-01`,
                    to_date: `${year}-12-31`,
                    event: event,
                    gender: gender,
                    estament: 'MASTE',
                    show_first: '10000'
                });
            }
        }
    }

    public async listMarks(): Promise<any[]> {
        return await db.select().from(marksEntity);
    }
}

export const feganService = new FeganService();