#stage 1
FROM node:latest as node
WORKDIR /app
COPY ./ez-shopping-webapp/package.json /app/
RUN npm install
RUN npm run build --prod
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/demo-app /usr/share/nginx/html