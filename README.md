## URL Shortener

# Solution

## url-shortener-api  (SpringBoot Groovy + Redis)

Simple rest api which shorten the url and keep in a redis database.

* ```sh 
curl -X POST -d '{"url":"https://www.google.com"}' -H "Content-Type: application/json" http://localhost:8080/api/urls
```
* ```
sh curl http://localhost:8080/api/urls/{hash}
```

## url-shortener-front (React + Nodejs application)

NodeJS is serving the react application which is calling the api to generate the hash given a url, and also handling the routes.

* http://localhost:3000 - render react application
* http://localhost:3000/{hash} - fetch from api and redirect to target url 
* http://localhost:3000/404.html


# How to run locally

To run locally docker and jdk8 are required.

```sh
$ ./url-shortener-api/gradlew build -p url-shortener-api
$ docker-compose build
$ docker-compose up
```
