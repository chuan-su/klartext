#!/bin/bash
printf "Starting database reset ...\n"
rake db:reset
printf "Starting database reset ... done\n"
printf "Starting elasticsearch setup\n"
rake es:setup
printf "Starting elasticsearch setup ... done\n"
printf "Starting downloading online resources ...\n"
rake dict:download
rake dict:download[en]
rake dict:download_examples
printf "Starting downloading online resources ... done\n"
printf "Starting importing posts to database ...\n"
rake db:import_posts[examples.csv]
printf "Starting importing posts to database ...done\n"
printf "Start syncing words,posts to elasticsearch ...\n"
rake es:sync_words
rake es:sync_posts
printf "Start syncing words,posts to elasticsearch ...done\n"

