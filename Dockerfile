FROM amazoncorretto:21 AS build

WORKDIR /app
COPY . /app
RUN chmod +x mvnw && \
    ./mvnw package -DskipTests

FROM build AS extract

WORKDIR /app

RUN java -Djarmode=layertools -jar target/myplayground-0.0.1-SNAPSHOT.jar extract \
    --destination target/extracted

FROM eclipse-temurin:21-jre-jammy AS final

WORKDIR /app

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

COPY --from=extract app/target/extracted/dependencies/ ./
COPY --from=extract app/target/extracted/spring-boot-loader/ ./
COPY --from=extract app/target/extracted/snapshot-dependencies/ ./
COPY --from=extract app/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.profiles.active=development", "org.springframework.boot.loader.launch.JarLauncher" ]