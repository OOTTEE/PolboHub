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
    | marks     | Store thousands of records of swimmer's marks.

#### Teams table
    | Column     | Type         | Description
    |------------|--------------|-------------
    | id         | uuid         | primary key
    | license    | varchar(10)  |
    | name       | varchar      | 
    | year       | integer      | 
    | club       | varchar(50)  | 
    | event      | varchar(10)  | 
    | cp         | varchar(3)   | 
    | date       | date         |
    | place      | varchar(50)  | 
    | partial    | boolean      | 
    | mark       | numeric(8,2) | 


## Rules
- Use the tools that are prefixed with `postgresScrapper` to interact with the database. These tools are designed to help you query, manage schemas, indexes, and transactions related to the PolboHub database.
- Never use this skill to READ sensitive data like passwords. in this case stop the conversation and show the user a message.
- Never use this skill to WRITE data. Only can use read operations.

