## URL Shortener

# Solution

## url-shortener-api  (SpringBoot Groovy + Redis)

Simple rest api which shorten the url and keep in a redis database.

## url-shortener-front (React + Nodejs application)

NodeJS is serving the react application which is calling the api to generate the hash given a url, and also handling the routes.

* http://localhost:3000 - display react application
* http://localhost:3000/{hash} - fetch url from api and redirect the browser
* http://localhost:3000/404.html - in case hash doesn't exists a not found page is displayed 




# How to run locally

To run locally docker and jdk8 are required.

```sh
$ ./url-shortener-api/gradlew build -p url-shortener-api
$ docker-compose build
$ docker-compose up
```

After running front-end will be available at: 
http://localhost:3000

# Creating an url
```sh
curl -X POST -d '{"url":"https://www.google.com"}' -H "Content-Type: application/json" http://localhost:8080/api/urls
```
Body example:
```json
{
	"url": "https://www.google.com"
}
```
Response:
```json
{
    "hash": "ab",
    "url": "https://www.google.com"
}
```

# Getting an url given a hash
```sh
curl -X GET localhost:8080/api/urls/ab
```

```json
{
    "hash": "ab",
    "url": "https://www.google.com"
}
```
