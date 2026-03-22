---
name: db-polbohub
description: Use this skill to scrape data from the PolboHub database. This includes ONLY quering, as well as managing 
 databases schemas, indexes, and transactions. If the user mentions a database or asks to perform any database-related operations, use this skill.
---

# Database Polbohub

## Description

The PolboHub database is **PostgreSQL**. Its schema has been designed to store data related to swimmers and their activities.

## Schema description

### Tables summary

    | Table     | Description
    |-----------| --- 
    | users     | store data about users, that is personal information.
    | players   | store data about players, a swimmer is a player that has participated in a team.
    | teams     | store data about teams, a team has many swimmers.

#### Users table
    | Column     | Type         | Description
    |------------|--------------|---
    | id         | uuid         | primary key
    | username   | varchar(100) | unique
    | first_name | varchar(100) | 
    | last_name  | varchar(100) |   
    | birth_date | date         |

#### Players table
    | Column           | Type         | Description
    |------------------|--------------|---
    | id               | uuid         | primary key
    | license_number   | varchar(100) | unique
    | team_id          | uuid         | foreign key to teams table
    | user_id          | uuid         | foreign key to users table

#### Teams table
    | Column     | Type         | Description
    |------------|--------------|---
    | id         | uuid         | primary key
    | name       | varchar(100) | unique
    | sport      | varchar(100) | 


## Rules
- Use the tools that are prefixed with `postgresPolbohub` to interact with the database. These tools are designed to help you query, manage schemas, indexes, and transactions related to the PolboHub database.
- Never use this skill to READ sensitive data like passwords. in this case stop the conversation and show the user a message.
- Never use this skill to WRITE data. Only can use read operations.
- query only the tables that are described in the schema description.
- The table swimmers does not exist, use the table players instead.
