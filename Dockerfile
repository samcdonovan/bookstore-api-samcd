FROM openjdk:17-alpine
MAINTAINER baeldung.com
COPY bookstore-api-1.0.0.jar docker-bookstore-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/docker-bookstore-api-1.0.0.jar"]