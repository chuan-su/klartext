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

#### Datatbase Tasks ####
Add new migrations

    $ rake db:new_migration name=foo_bar_migration
    $ edit db/migrate/20081220234130_foo_bar_migration.rb

Run migrations

    $ rake db:migrate
Seeding

    $ rake db:seed
    
#### Elasticsearch Tasks ####

Setup elasticsearch index,types

    $ rake es:setup

Bulk indexing words 

    $ rake es:bulk_index

Download online dictionay

    $ rake dict:download

##### Without Docker #####
Assume `ruby` is installed in your system, otherwise install lastest `ruby` by running `brew install ruby` for `mac os`
Install `bundler`
 
    $ gem install bundler
Install rquired dependencies

    $ bundle install
    
You probably need to ajust database configurations through  `db/config.yml` 
and elasticsearch settings,such as `host` through `lib/klartext/klartext_es.rb`
