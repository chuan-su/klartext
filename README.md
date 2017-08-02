# klartext
> Learn swedish på ett lite lugnare sätt :)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The project is built with `docker`. Therefore [docker](https://www.docker.com) is required to be installad on your machine.

### Up and running

This section provides step by step guidline on getting a development env running.

The project is built with 3 components:

  * [backend](https://github.com/chuan-su/klartext/tree/master/backend/README.md)      - RESTful web service with [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
  * [frontend](https://github.com/chuan-su/klartext/tree/master/frontend/README.md)     - A [Vue](https://vuejs.org/v2/guide/) app.
  * [command line](https://github.com/chuan-su/klartext/blob/master/ruby-scripts/README.md) - Tasks automation, such as data processing, database migration and elasticsearch setup
  
Database and Elasticsearch server bootstrap

    $ docker-compose run --rm ruby sh ./clean_db_es_bootstrap.sh

Start Spring boot backend, default host [localhost:8080](http://localhost:8080)

    $ docker-compose up backend
    
Start Frontend Vue App, default host [localhost:5000](http://localhost:5000)
    
    $ docker-compose up frontend

Verify docker containers running correctly
    
    $ docker ps
    
you should be able to see 4 containers listed, `klartext_frontend`,`klartext_backend`,`klartext_es` and `mysql`

### Clean up docker daemon data

    $ docker system prune

**[docker system prune](https://docs.docker.com/engine/reference/commandline/system_prune/)** will delete ALL unused data (i.e. In order: containers stopped, volumes without containers and images with no containers).

You also have:

  * [docker container prune](https://docs.docker.com/engine/reference/commandline/container_prune/)
  * [docker image prune](https://docs.docker.com/engine/reference/commandline/image_prune/)
  * [docker network prune](https://docs.docker.com/engine/reference/commandline/network_prune/)
  * [docker volume prune](https://docs.docker.com/engine/reference/commandline/volume_prune/)

For unused images, use `docker image prune -a` (for removing dangling and ununsed images).

**Warning**: *unused* means *images not referenced by any container*: be careful before using `-a`

## REST API

REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

       

    

    
    

