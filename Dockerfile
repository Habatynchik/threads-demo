FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]