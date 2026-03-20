import {FastifyPluginAsync, FastifyReply, FastifyRequest, RouteShorthandOptions} from 'fastify'
import {feganService} from "../../services/fegan/fegan.service";

const schema: RouteShorthandOptions = {
    schema: {
        querystring: {
            type: 'object',
            properties: {
                page: {type: 'integer', default: 1},
                limit: {type: 'integer', default: 10},
                license: {type: 'string'},
                name: {type: 'string'},
                year: {type: 'integer'},
                fromYear: {type: 'integer'},
                toYear: {type: 'integer'},
                club: {type: 'string'},
                event: {type: 'string'},
                cp: {type: 'string'},
                date: {type: 'string', format: 'date'},
                fromDate: {type: 'string', format: 'date'},
                toDate: {type: 'string', format: 'date'},
                place: {type: 'string'},
                partial: {type: 'boolean'},
                minMark: {type: 'number', format: 'float'},
                maxMark: {type: 'number', format: 'float'}
            }
        },
        response: {
            200: {
                type: 'object',
                properties: {
                    data: {
                        type: 'array',
                        items: {
                            type: 'object',
                            properties: {
                                id: {type: 'integer'},
                                licence: {type: 'integer'},
                                name: {type: 'string'},
                                year: {type: 'number'},
                                club: {type: 'string'},
                                event: {type: 'string'},
                                cp: {type: 'string'},
                                date: {type: 'string', format: 'date-time'},
                                place: {type: 'string'},
                                partial: {type: 'boolean'},
                                mark: {type: 'number'},
                                gender: {type: 'string'}
                            }
                        }
                    },
                    pagination: {
                        type: 'object',
                        properties: {
                            totalItems: {type: 'integer'},
                            totalPages: {type: 'integer'},
                            currentPage: {type: 'integer'},
                            itemsPerPage: {type: 'integer'}
                        }
                    }
                }
            }
        }
    }
}

interface MarkQueryParams {
    page: number
    limit: number
    license: string
    name: string
    year: number
    fromYear: number
    toYear: number
    club: string
    event: string
    cp: string
    date: string
    fromDate: string
    toDate: string
    place: string
    partial: boolean
    minMark: number
    maxMark: number
}

const marks: FastifyPluginAsync = async (fastify, opts) => {
    fastify.get('/', schema, async (request: FastifyRequest, reply: FastifyReply) => {
        const params: MarkQueryParams = request.query as MarkQueryParams;

        try {
            return await feganService.listMarksPaged(
                params.page,
                params.limit,
                {
                    license: params.license,
                    name: params.name,
                    year: params.year,
                    fromYear: params.fromYear,
                    toYear: params.toYear,
                    club: params.club,
                    event: params.event,
                    cp: params.cp,
                    date: params.date,
                    fromDate: params.fromDate,
                    toDate: params.toDate,
                    place: params.place,
                    partial: params.partial,
                    minMark: params.minMark,
                    maxMark: params.maxMark
                });
        } catch (error) {
            fastify.log.error(error);
            return reply.status(500).send({error: 'Error interno del servidor'});
        }
    })
}

export default marks