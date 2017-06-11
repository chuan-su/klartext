# Klartext::Cli

## Install ##

### Docker ###

    $ cd ..
    $ docker-compose run ruby bash

### Not with Docker ###

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

Seeding

    $ rake db:seed
    
### Elasticsearch Tasks ###

Setup elasticsearch index,types

    $ rake klartext:es_setup
