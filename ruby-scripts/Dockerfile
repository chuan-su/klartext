FROM ruby:2.4.1-alpine

RUN apk add --update \
    ruby-dev \
    build-base \
    mysql-dev \
    && rm -rf /var/cache/apk/
    
RUN mkdir -p /klartext/ruby-scripts
ADD . /klartext/ruby-scripts
WORKDIR /klartext/ruby-scripts

RUN bundle install


