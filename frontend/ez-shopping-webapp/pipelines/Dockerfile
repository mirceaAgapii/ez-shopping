# Stage 1: Build an Angular Docker Image
FROM node:16-buster as build
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY . /app
ARG configuration=production
RUN npm run build -- --outputPath=./dist/out --configuration $configuration
# Stage 2, use the compiled app, ready for production with Nginx
FROM nginx
ARG configuration=production
COPY --from=build /app/dist/out/ /usr/share/nginx/html
COPY /pipelines/nginx-custom-$configuration.conf /etc/nginx/conf.d/default.conf