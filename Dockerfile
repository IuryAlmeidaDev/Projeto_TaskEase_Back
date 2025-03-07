
FROM ubuntu:latest AS build


WORKDIR /app


RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven


COPY . .


RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-slim


WORKDIR /app


EXPOSE 8080


COPY --from=build /app/target/TaskEase-1.0.0-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]