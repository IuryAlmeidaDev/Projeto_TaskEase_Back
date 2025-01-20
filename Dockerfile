FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

WORKDIR /app
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

# Adicionando comando para listar arquivos no diretório /app/target
RUN ls -l /app/target

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/TaskEase-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
