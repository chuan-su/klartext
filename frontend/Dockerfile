FROM node:6.11.0-alpine
RUN mkdir -p /klartext/frontend
WORKDIR /klartext/frontend
COPY package.json /klartext/frontend

RUN npm install

COPY . /klartext/frontend

EXPOSE 5000
CMD ["npm","start"]
