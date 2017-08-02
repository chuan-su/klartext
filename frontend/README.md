# klartext frontend
> A Vue.js application

## REST API

Backend by default is hosted on port `8080`, [localhost:8080](http://localhost:8080)

REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

## Development

  * Bootstrap Database and Elasticsearch, you only need to run it once.
    
        $ docker-compose run --rm ruby sh ./clean_db_es_bootstrap.sh
  
  * Start backend server
    
        $ cd ./backend
        $ ./gradlew assemble
        $ docker-compose up --build -d backend

  * Start frontend server

        $ docker-compose up frontend

    * You can also choose to `bash` into docker container, then start server with `yarn start`
    
        $ docker-compose run --rm --service-ports frontend sh
        $ yarn start

Server with hot reload can be accessed at [localhost:5000](http://localhost:5000)

### Install node modules
Make sure you have a running container then install `node module` with:

    $ docker-compose exec frontend yarn add node-sass

### Stop docker container

    $ docker-compose stop frontend
    
Instructions on cleaning docker daemon data can be found [here](https://github.com/chuan-su/klartext/blob/master/README.md#clean-up-docker-daemon-data)
