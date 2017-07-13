#!/bin/bash
rake db:reset
rake es:setup
rake dict:download
rake dict:download[en]
rake dict:download_examples
rake db:import_posts[examples.csv]
rake es:sync_words
rake es:sync_posts

