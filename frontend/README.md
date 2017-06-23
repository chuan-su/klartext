# klartext frontend
> A Vue.js application

## Build Setup
    
    $ docker-compose up frontend
or

    $ docker-compose run --rm --service-ports frontend

serve with hot reload at localhost:5000

### Start backend daemon
    
    $ cd ../backend
    $ ./gradlew assemble
    $ docker-compose up -d backend
    

