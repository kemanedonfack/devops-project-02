FROM node:16-alpine as build
COPY . .
RUN npm install
RUN npm run build

FROM node:16-alpine as release
COPY --from=build /build ./build
RUN npm install -g serve
EXPOSE 3000
CMD [ "serve", "-s", "build" ]