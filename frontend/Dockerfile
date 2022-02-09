###    1. build react frontend

FROM node:16-alpine AS builder
WORKDIR /app
COPY . .
RUN yarn install --frozen-lockfile
RUN yarn build



###    2. build nginx server to host frontend

FROM nginx:alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build .

EXPOSE 3000
ENTRYPOINT ["nginx", "-g", "daemon off;"]
