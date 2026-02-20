CREATE TABLE IF NOT EXISTS users (
    id               uuid PRIMARY KEY,
    username         VARCHAR(100) NOT NULL UNIQUE,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    birth_date       DATE NOT NULL
);