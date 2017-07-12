# klartext
> Learn swedish på ett lite lugnare sätt :)

### Clean Database and Elasticsearch bootstrap
    
    $ docker-compose run --rm ruby sh ./clean_db_es_bootstrap.sh

Learn more about the klartext `command line tasks` [here](https://github.com/chuan-su/klartext/tree/master/ruby-scripts/README.md)

### Start Spring boot backend

    $ docker-compose up backend
And you should be able to see 3 docker containers `klartext_backend`,`klartext_es` and `mysql`up running by `docker ps`

Learn more about `backend build` for development [here](https://github.com/chuan-su/klartext/tree/master/backend/README.md)

#### REST API
REST API Documentation can be found [here](https://github.com/chuan-su/klartext/blob/master/backend/API.md)

### Start Frontend Vue App
    
    $ docker-compose up frontend
    
Learn more about `frontend build`  [here](https://github.com/chuan-su/klartext/tree/master/frontend/README.md)
       

    

    
    

