#stage 1
FROM node:latest as node
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN npm install
RUN npm audit fix –force
RUN npm run build --prod
EXPOSE 4200


CMD ["npm", "start"]