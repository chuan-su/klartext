FROM node:6.11.2-alpine
RUN apk add --no-cache git
RUN mkdir -p /klartext/frontend

ADD . /klartext/frontend
WORKDIR /klartext/frontend

RUN yarn

EXPOSE 5000
CMD ["npm","run","dev"]
