# DEZSYS_GK862_WAREHOUSE_ORM
**Author**: Benjamin Princ

**Date**: 12.03.2024

- [DEZSYS\_GK862\_WAREHOUSE\_ORM](#dezsys_gk862_warehouse_orm)
  - [Introduction](#introduction)
  - [Work GKü](#work-gkü)
    - [Following the tutorial](#following-the-tutorial)
    - [Solving error](#solving-error)
  - [Sources](#sources)


## Introduction
The idea of the project is to demonstrate a connection between Java and databases(MySql, Postgres)

## Work GKü
At first a Postgres container gets created with the command `docker run --name warehouse_postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres`.

### Following the tutorial
When following the tutorial and want to use postgres one has to choose the postgres driver instead and setting up the user in this container.

### Solving error
```
2024-03-12 11:07:57 2024-03-12 10:07:57.781 UTC [216] STATEMENT:  create table users (id integer not null, email varchar(255), name varchar(255), primary key (id))

2024-03-12 11:07:57 2024-03-12 10:07:57.789 UTC [216] ERROR:  permission denied for schema public
```

Error, because the springuser didn´t has his privileges in the 'db_example'. So I needed to connect as postgres user to the database and grant the permissions

```
Hibernate: create table users (id integer not null, email varchar(255), name varchar(255), primary key (id))

Hibernate: create sequence users_seq start with 1 increment by 50
```

## Sources
[1], *Spring accessing data*, https://spring.io/guides/gs/accessing-data-mysql#scratch

[2], *Permission error*, https://stackoverflow.com/questions/67276391/why-am-i-getting-a-permission-denied-error-for-schema-public-on-pgadmin-4