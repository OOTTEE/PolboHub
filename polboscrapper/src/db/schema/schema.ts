import {
    pgTable,
    date,
    varchar,
    integer,
    boolean, numeric
} from 'drizzle-orm/pg-core'

export const marksEntity = pgTable('marks', {
    id: integer().primaryKey().generatedAlwaysAsIdentity(),
    licence: varchar('license', {length: 10} ),
    name: varchar('name', {length: 50} ),
    year: integer(),
    club: varchar('club', {length: 50} ),
    event: varchar('event', {length: 10} ),
    cp: varchar('cp', {length: 3} ),
    date: date({mode: "string"}),
    place: varchar('place', {length: 50} ),
    partial: boolean(),
    mark: numeric('mark', {precision: 8, scale: 2, mode: "string"} )
})