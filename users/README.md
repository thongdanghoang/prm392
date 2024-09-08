# Users Module

### How to run the users module shortcut

```shell script
JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437 POSTGRES_HOST=localhost POSTGRES_PORT=5432 POSTGRES_DB=prm392 POSTGRES_USER=postgres POSTGRES_PASSWORD=postgres ./mvnw spring-boot:run 
```

## Overview

This module is responsible for managing users profile, authentication and authorization for the `prm392` project.

## Prerequisites

- Java 21
- Maven
- PostgreSQL installed and running (local or Docker)

## Configuration

Ensure you have a `.env` file in the root of your project with the following environment variables:

```dotenv
POSTGRES_HOST=your_host
POSTGRES_PORT=your_port
POSTGRES_DB=your_db
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_secret
```