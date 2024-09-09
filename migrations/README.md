# Migrations Module

### How to run the migrations shortcut

```shell script
POSTGRES_HOST=localhost POSTGRES_PORT=5432 POSTGRES_DB=prm392 POSTGRES_USER=postgres POSTGRES_PASSWORD=postgres ./mvnw spring-boot:run 
```

## Overview

This module is responsible for managing database migrations for the `prm392` project. It uses Flyway for version control of the database schema.

## Prerequisites

- Java 21
- Maven
- PostgreSQL installed and running (local or Docker)

## Dependencies

The following dependencies are used in this module:

- `spring-boot-starter-data-jpa`
- `postgresql`
- `flyway-core`
- `flyway-database-postgresql`

## Configuration

Ensure you have a `.env` file in the root of your project with the following environment variables:

```dotenv
POSTGRES_HOST=your_host
POSTGRES_PORT=your_port
POSTGRES_DB=your_db
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
```