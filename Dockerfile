# Dockerfile for Spring Boot Application
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
