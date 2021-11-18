FROM node:16-alpine

WORKDIR /app

COPY ./quickcheck/package*.json ./

RUN yarn install

# overwritten in dev-container by mount
COPY ./quickcheck .

EXPOSE 3000

CMD ["yarn", "start"]