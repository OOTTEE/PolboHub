import {FastifyPluginAsync, RouteShorthandOptions} from 'fastify'
import {feganService} from "../../services/fegan/fegan.service";

const schema: RouteShorthandOptions = {
    schema: {
        querystring: {
            type: 'object',
            properties: {
                year: {type: 'integer'}
            },
            required: ['year']
        },
        response: {
            200: {
            }
        }
    }
}

const marks: FastifyPluginAsync = async (fastify, opts) => {

    fastify.post('/scrap', schema, async (request, reply) => {
        const { year } = request.query as { year: number };
        await feganService.scrapMarksFromYear(year);
        return feganService.listMarks();
    })

}

export default marks