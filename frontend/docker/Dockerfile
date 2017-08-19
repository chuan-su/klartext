FROM node:6.11.2-alpine
RUN mkdir -p /klartext/frontend

ADD . /klartext/frontend
WORKDIR /klartext/frontend

RUN yarn
RUN yarn global add react-scripts


EXPOSE 5000
CMD ["yarn","start"]
