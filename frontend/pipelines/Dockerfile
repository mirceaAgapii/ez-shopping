#stage 1
FROM node:16 as node
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN npm cache clean --force
RUN npm install
RUN npm run build --prod
RUN npm set registry http://registry.npmjs.org/ 
COPY . .


#Download NGINX Image
FROM nginx

#Copy built angular files to NGINX HTML folder
COPY --from=node /app/dist/ez-shopping-webapp /usr/share/nginx/html
EXPOSE 80
