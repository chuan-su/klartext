# klartext
Learn swedish på ett lite lugnare sätt :)

## Up and running ##

    $ docker-compose up

### Start Spring boot backend ###

    $ docker-compose run --rm --service-ports backend

And you should be able to see 3 docker containers `klartext_es`,`mysql` and `klartext_backend` up

    $ docker ps
    
#### Execute command line scripts ####

Command line scripts involves tasks of databae migration,seeding and setting up elasticsearch index.

DB migration, seed

    $ docker-compose run --rm ruby db:migrate
    $ docker-compose run --rm ruby db:seed

Elasticsearch Index setup

    $ docker-compose run --rm ruby rake klartext:es_setup

Execute scripts in docker container
    
    $ docker-compose run --rm ruby sh
    
Learn more about the command line tasks [here](https://github.com/chuan-su/klartext/tree/master/ruby-scripts)

### Frontend App ###
    
        

    

    
    

