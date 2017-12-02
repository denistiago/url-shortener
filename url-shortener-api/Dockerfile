FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY build/libs/*.jar /usr/src/app/url-shortener.jar
CMD java -jar /usr/src/app/url-shortener.jar