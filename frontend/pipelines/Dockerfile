#stage 1
FROM node:16.14.2-alpine3.15 as node
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod
EXPOSE 4200


CMD ["npm", "start"]