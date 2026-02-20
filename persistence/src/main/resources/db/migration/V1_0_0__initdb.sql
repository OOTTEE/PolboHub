CREATE TYPE sport AS ENUM ('SWIMMING', 'TENNIS', 'CYCLING');

CREATE TABLE IF NOT EXISTS users (
    id               uuid PRIMARY KEY,
    username         VARCHAR(100) NOT NULL UNIQUE,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    birth_date       DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS teams (
    id    uuid PRIMARY KEY,
    name  VARCHAR(100) NOT NULL UNIQUE,
    sport sport NOT NULL
);

CREATE TABLE IF NOT EXISTS players (
    id          uuid PRIMARY KEY,
    license_number VARCHAR(100) UNIQUE,
    team_id        uuid REFERENCES teams(id),
    user_id        uuid REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS statics (
    id          uuid PRIMARY KEY,
    name        varchar (100) NOT NULL UNIQUE,
    description varchar (1000),
    sport       sport NOT NULL,
    type        varchar (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS statics_values (
    id          uuid PRIMARY KEY,
    static_id   uuid REFERENCES statics(id),
    player_id   uuid REFERENCES players(id),
    value       varchar (100) NOT NULL,
    date       DATE NOT NULL
);
