## Build Stage - Use an image with Gradle installed
#FROM gradle:8.6-jdk21 AS build
#
## Set the working directory
#WORKDIR /app
#
## Copy your project files
#COPY . /app
#
## Build your project with Gradle
#RUN gradle build
#
## Runtime Stage - Use a smaller JRE image
##FROM openjdk:21-jre-alpine
#
## Set the working directory
#WORKDIR /app
#
## Copy the built artifacts from the build stage
#COPY --from=build /app/build/libs/*.jar app.jar
#
## Command to start the Spring Boot app
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM gradle:8.6-jdk21
EXPOSE 8080
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]