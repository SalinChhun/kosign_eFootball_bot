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

# Stage 1: Build the application using bootJar
FROM gradle:8.6-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/build/libs/telegram-bot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

