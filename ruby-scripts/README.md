# Klartext Command Line Tool
> Command line scripts involves tasks of databae migration,seeding and setting up elasticsearch index.

### Usage ###
The easiest way to execute command-line tasks is through `docker`, either with 
`docker-compose run --rm ruby <rake_task_name>`, or `bash` into docker container with:

    $ docker-compose run --rm ruby sh 

And View all rake tasks

    $ rake -T
    
If you don't use docker then you need to have `ruby` and `bundler`
installed in your system, instructions can be found at the bottom.

#### Download and parse online resources, such as dictionay and examples ####

    $ rake dict:download
    $ rake dict:download_examples

#### Datatbase Tasks ####
Add new migrations

    $ rake db:new_migration name=foo_bar_migration
    $ edit db/migrate/20081220234130_foo_bar_migration.rb
Run migrations

    $ rake db:migrate
Seeding

    $ rake db:seed
Clean db setup
    
    $ rake db:reset
Import post csv data to database and elasticsearch

    $ rake db:import_posts[examples.csv]

#### Elasticsearch Tasks ####

Setup elasticsearch index,types

    $ rake es:setup
Sync words/vocabularies from downloaded dictionary

    $ rake es:sync_words
Sync posts data to elasticsearch
    
    $ rake es:sync_posts
    
### Without Docker ###
Assume `ruby` is installed in your system, otherwise install lastest `ruby` by running `brew install ruby` for `mac os`
Install `bundler`
 
    $ gem install bundler
Install required dependencies

    $ bundle install
    
You probably need to ajust database configurations through  `db/config.yml` 
and elasticsearch settings,such as `host` through `lib/klartext/klartext_es.rb`
