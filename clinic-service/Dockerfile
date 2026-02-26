FROM maven:3.9.10-amazoncorretto-21 AS build
ENV HOME=/app
WORKDIR $HOME
ADD . $HOME
RUN mvn -f pom.xml -pl :clinic-service -am install -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/clinic-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]