# DEZSYS_GK862_WAREHOUSE_ORM
**Author**: Benjamin Princ

**Date**: 12.03.2024

- [DEZSYS\_GK862\_WAREHOUSE\_ORM](#dezsys_gk862_warehouse_orm)
  - [Introduction](#introduction)
  - [Work GKü](#work-gkü)
    - [Following the tutorial](#following-the-tutorial)
    - [Solving error](#solving-error)
  - [Work GKv](#work-gkv)
    - [New entities](#new-entities)
    - [Problem, endless relations](#problem-endless-relations)
    - [Mapping methods](#mapping-methods)
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

## Work GKv
The warehouse application should be implemented in this project.

### New entities
One entity is needed for the warehouse and one for the products. From the warehouse perspective, we have a One-To-Many relation, which means one warehouse can hold many products. Additionaly the cascade parameter automates operations if a connected entity experiences changes.
From the products perspective, a Many-To-One relation, describes that many products can be in one warehouse.

````java
@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Products> products;
````

````java
@ManyToOne
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;
````

### Problem, endless relations
With the relation between these two entities, a endless loop got created when calling the `/all`. To fix this, i simply deleted the `getWarehouse()` method in the product entity.

### Mapping methods
```java
addNewWarehouse (@RequestParam String name, @RequestParam String address, @RequestParam Integer zip, @RequestParam String country, @RequestParam String city){}
```
This method makes it possible to create new warehouses via a RequestMapping.

```java
addNewProduct (@RequestParam String name, @RequestParam String category, @RequestParam Integer quantity, @RequestParam String unit, @RequestParam Integer warehouseId){}
```
This method makes it possible to create new product and links them to the warehouse via their id.


## Sources
[1], *Spring accessing data*, https://spring.io/guides/gs/accessing-data-mysql#scratch

[2], *Permission error*, https://stackoverflow.com/questions/67276391/why-am-i-getting-a-permission-denied-error-for-schema-public-on-pgadmin-4