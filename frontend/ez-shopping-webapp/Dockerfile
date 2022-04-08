FROM node as build
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build --prod

FROM nginx
COPY --from=build /app/dist/tfh-frontend /usr/share/nginx/html
EXPOSE 80