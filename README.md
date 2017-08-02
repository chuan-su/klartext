# klartext
> Learn swedish på ett lite lugnare sätt :)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The project is built with `docker`. Therefore [docker](https://www.docker.com) is required to be installad on your machine.

### Up and running

This section provides step by step guidline on getting a development env running.

The project is buit with 3 components:

  * [backend](https://github.com/chuan-su/klartext/tree/master/backend/README.md)      - RESTful web service with [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
  * [frontend](https://github.com/chuan-su/klartext/tree/master/frontend/README.md)     - A [Vue](https://vuejs.org/v2/guide/) app.
  * [command line](https://github.com/chuan-su/klartext/blob/master/ruby-scripts/README.md) - Tasks automation, such as data processing, database migration and elasticsearch setup
  
Database and Elasticsearch bootstrap

    $ docker-compose run --rm ruby sh ./clean_db_es_bootstrap.sh

Start Spring boot backend

    $ docker-compose up backend
    
Start Frontend Vue App
    
    $ docker-compose up frontend

Verify docker containers running correctly
    
    $ docker ps
    
you should be able to see 4 containers listed, `klartext_frontend`,`klartext_backend`,`klartext_es` and `mysql`

## REST API

REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

       

    

    
    

