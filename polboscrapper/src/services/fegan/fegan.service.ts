import {EventCode, feganScrapperService, GenderCode, MAPPINGS} from "./feganScrapper.service";
import {db} from "../../db";
import {marksEntity} from "../../db/schema/schema";
import {and, count, eq, gte, like, lte} from "drizzle-orm";


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

    public async listMarksPaged(page: number, limit: number, filter: {
        license: string,
        name: string,
        year: number,
        fromYear: number,
        toYear: number,
        club: string,
        event: string,
        cp: string,
        date: string,
        fromDate: string,
        toDate: string,
        place: string,
        partial: boolean,
        minMark: number,
        maxMark: number
    }): Promise<any> {
        const offset = (page - 1) * limit;
        const conditions = [];

        if (filter.license !== undefined) conditions.push(eq(marksEntity.licence, filter.license));
        if (filter.year !== undefined)    conditions.push(eq(marksEntity.year, filter.year));
        if (filter.cp !== undefined)      conditions.push(eq(marksEntity.cp, filter.cp));
        if (filter.date !== undefined)    conditions.push(eq(marksEntity.date, filter.date));
        if (filter.partial !== undefined) conditions.push(eq(marksEntity.partial, filter.partial));


        if (filter.name)  conditions.push(like(marksEntity.name, `%${filter.name}%`));
        if (filter.club)  conditions.push(like(marksEntity.club, `%${filter.club}%`));
        if (filter.event) conditions.push(like(marksEntity.event, `%${filter.event}%`));
        if (filter.place) conditions.push(like(marksEntity.place, `%${filter.place}%`));


        if (filter.fromYear) conditions.push(gte(marksEntity.year, filter.fromYear));
        if (filter.toYear)   conditions.push(lte(marksEntity.year, filter.toYear));
        if (filter.minMark)  conditions.push(gte(marksEntity.mark, filter.minMark.toString()));
        if (filter.maxMark)  conditions.push(lte(marksEntity.mark, filter.maxMark.toString()));


        if (filter.fromDate) conditions.push(gte(marksEntity.date, filter.fromDate));
        if (filter.toDate)   conditions.push(lte(marksEntity.date, filter.toDate));

        const whereClause = conditions.length > 0 ? and(...conditions) : undefined;

        const [data, totalResult] = await Promise.all([
            db.select()
                .from(marksEntity)
                .where(whereClause)
                .limit(limit).offset(offset),
            db.select({value: count()})
                .from(marksEntity)
                .where(whereClause)
        ]);

        const totalItems = totalResult[0].value;
        const totalPages = Math.ceil(totalItems / limit);

        return {
            data,
            pagination: {
                totalItems,
                totalPages,
                currentPage: page,
                itemsPerPage: limit
            }
        };

    }
}

export const feganService = new FeganService();