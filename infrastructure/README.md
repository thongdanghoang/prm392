# Docker Compose Setup for PostgreSQL

## Shortcut

Running the Docker Compose Setup:

```shell script
docker-compose up -d 
```

Stopping the Docker Compose Setup

```shell script
docker-compose down
```

## Overview

This setup uses Docker Compose to run a PostgreSQL database container. The environment variables for the PostgreSQL
database are configured using a `.env` file.

## Prerequisites

- Docker
- Docker Compose

## Configuration

Ensure you have a `.env` file in the root of your project with the following environment variables:

```dotenv
POSTGRES_DB=your_db
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
```