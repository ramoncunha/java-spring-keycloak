FROM amazoncorretto:21 AS build

WORKDIR /app
COPY . /app
RUN ["chmod", "+x", "mvnw"]
RUN ["./mvnw", "install"]

FROM amazoncorretto:21-alpine-jdk AS dev

WORKDIR /app
COPY --from=build /app/target/myplayground-0.0.1-SNAPSHOT.jar /app/car-api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=default", "-jar", "car-api.jar"]