FROM node:latest as build
LABEL authors="Hamza"
WORKDIR /usr/local/app
COPY ./ /usr/local/app
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/tp4-ui/browser /usr/share/nginx/html

EXPOSE 80
