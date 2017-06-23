# Klartext Command Line Tool
> Command line scripts involves tasks of databae migration,seeding and setting up elasticsearch index.

### Install with Docker ###

    $ cd ..
    $ docker-compose run --rm ruby sh

### Without Docker ###

Assume `ruby` is installed in your system, otherwise install lastest `ruby` by running `brew install ruby` for `mac os`

Install `bundler`
    
    $ gem install bundler
    
Install rquired dependencies

    $ bundle install
    

## Usage ##

View all rake tasks

    $ rake -T

### Datatbase Tasks ###

Add new migrations

    $ rake db:new_migration name=foo_bar_migration
    $ edit db/migrate/20081220234130_foo_bar_migration.rb

Run migrations

    $ rake db:migrate

Seeding

    $ rake db:seed
    
### Elasticsearch Tasks ###

Setup elasticsearch index,types

    $ rake es:setup
    
Bulk indexing words 

    $ rake es:bulk_index
    
Download online dictionay

    $ rake dict:download
