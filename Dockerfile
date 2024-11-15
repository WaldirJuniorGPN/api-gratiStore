FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /api-gratiStore
COPY /pom.xml .
COPY /src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /api-gratiStore/target/*.jar /api-gratiStore/api-gratiStore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/api-gratiStore/api-gratiStore.jar"]