CREATE TABLE IF NOT EXISTS users (
    id               uuid PRIMARY KEY,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    birth_date       DATE NOT NULL,
    license_number   VARCHAR(50) NOT NULL UNIQUE,
    active           BOOLEAN NOT NULL DEFAULT TRUE
);