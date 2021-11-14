FROM node:16-alpine

WORKDIR /app

COPY ./quickcheck/package*.json ./

RUN yarn install

# use copy instead of mount real deployment
# COPY ./quickcheck .

EXPOSE 3000

CMD ["yarn", "start"]