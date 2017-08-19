# klartext frontend
> A React App

This project was bootstrapped with [Create React App](https://github.com/facebookincubator/create-react-app). 
You can find the most recent version of its guide [here](https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md).

## Development

  * Bootstrap Database and Elasticsearch, you only need to run it once.
    
        $ docker-compose run --rm ruby sh ./clean_db_es_bootstrap.sh
  
  * Start backend server in background

        $ docker-compose up --build -d backend

  * Start frontend server
  
        $ docker-compose up --build frontend

    * You can also choose to `bash` into docker container, then start server with `yarn start`
    
          $ docker-compose run --rm --service-ports frontend sh
          $ yarn start
          
  * Stop frontend server
          
        $ docker-compose stop frontend
  
  * Clean up unused docker daemon data
  
        $ docker system prune
    
    Learn moore about cleaning docker daemon data  [here](https://github.com/chuan-su/klartext/blob/master/README.md#clean-up-docker-daemon-data)

Server with hot reload can be accessed at [localhost:5000](http://localhost:5000)

### Install node modules
Make sure you have a running container before running the command.

    $ docker-compose exec frontend yarn add <node_module>

### Build for Production
Builds the app for production to the `build` folder.
Make sure you have a running container before running the command.

    $ docker-compose exec frontend yarn build
    
### REST API
Backend by default is hosted on port `8080`, [localhost:8080](http://localhost:8080)

REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

