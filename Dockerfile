FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

# Certifique-se de que o nome do arquivo JAR está correto
COPY --from=build /target/todolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
