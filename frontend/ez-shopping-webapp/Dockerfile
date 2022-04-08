FROM node as build

RUN mkdir -p /app

WORKDIR /app

COPY ./package*.json /app

RUN npm install

	
RUN npm run build --prod

EXPOSE 4200

CMD ["npm", "start"]