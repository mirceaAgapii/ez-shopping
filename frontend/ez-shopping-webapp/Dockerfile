FROM node:latest
WORKDIR /app
RUN ls
# please note, you already declared a WORKDIR, 
# therefore your files will be automaticaly pushed to ./app
COPY . .
RUN npm install -g
COPY ./ ./ 
EXPOSE 4200
CMD ["npm", "start"]