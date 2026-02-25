import { FastifyPluginAsync } from 'fastify'
import {feganScrapperService} from "../../services/fegan/feganScrapper.service";

const marks: FastifyPluginAsync = async (fastify, opts) => {
  fastify.get('/', async (request, reply) => {
    return { hello: 'world' }
  })
  fastify.get('/scrap', async (request, reply) => {
    return feganScrapperService.scrapMarks();
  })
}

export default marks