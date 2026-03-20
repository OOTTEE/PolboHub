import {FastifyPluginAsync, FastifyReply, FastifyRequest, RouteShorthandOptions} from 'fastify'
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




const scrap: FastifyPluginAsync = async (fastify, opts) => {

    fastify.post('/', schema, async (request: FastifyRequest, reply: FastifyReply) => {
        const { year } = request.query as { year: number };
        await feganService.scrapMarksFromYear(year);
        return reply.status(200).send();
    })

}

export default scrap;