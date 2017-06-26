# klartext frontend
> A Vue.js application

## Up and running
    
    $ docker-compose up frontend

#### Stop docker container ####

    $ docker-compose stop frontend

or with bash access

    $ docker-compose run --rm --service-ports frontend sh
    $ yarn start
    $ exit

server with hot reload at [localhost:5000](http://localhost:5000)

### Start backend daemon
Most likely, you need to start backend server container

    $ cd ../backend
    $ ./gradlew assemble
    $ docker-compose up -d backend

#### REST API
REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

