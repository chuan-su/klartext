FROM node:6.11.0-alpine
RUN mkdir -p /klartext/frontend
WORKDIR /klartext/frontend

COPY . /klartext/frontend
RUN yarn install --pure-lockfile

EXPOSE 5000
CMD ["yarn","start"]
