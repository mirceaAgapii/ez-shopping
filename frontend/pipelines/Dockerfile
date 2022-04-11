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

RUN ls -l dist

#Download NGINX Image
FROM nginx:1.15.8-alpine

#Copy built angular files to NGINX HTML folder
COPY --from=node /dist/frontend/ /usr/share/nginx/html
EXPOSE 4200


CMD ["npm", "start"]
