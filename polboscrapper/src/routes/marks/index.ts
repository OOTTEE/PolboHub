import {FastifyPluginAsync, RouteShorthandOptions} from 'fastify'
import {feganService} from "../../services/fegan/fegan.service";

const schema: RouteShorthandOptions = {
  schema: {
    querystring: {
      type: 'object',
      properties: {
        year: { type: 'integer' }
      },
      required: ['year']
    },
    response: {
      200: {
        type: 'array',
        items: {
          type: 'object',
          properties: {
            licence: { type: 'integer'},
            name: { type: 'string'},
            year: { type: 'number'},
            club: { type: 'string'},
            event: { type: 'string'},
            cp: { type: 'string'},
            date: { type: 'string', format: 'date-time'},
            place: { type: 'string'},
            partial: { type: 'boolean'},
            mark: { type: 'number'}
          }
        }
      }
    }
  }
}

const marks: FastifyPluginAsync = async (fastify, opts) => {
  fastify.get('/', schema, async (request, reply) => {
    return feganService.listMarks();
  })
  fastify.post('/scrap', schema, async (request, reply) => {
    const { year } = request.query as { year: number };
    await feganService.scrapMarksFromYear(year);
    // feganService.scrapMarksMarti();

    return feganService.listMarks();
  })
}

export default marks