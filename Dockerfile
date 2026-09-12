# Stage 1: Build dell'applicazione con Maven e JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn ./.mvn

# Pre-download delle dipendenze per velocizzare i build successivi
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package -DskipTests -B

# Stage 2: Immagine Runtime minimale UBI9 OpenJDK 21
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24
ENV LANGUAGE='it_IT:it'
USER 185
WORKDIR /deployments

COPY --from=builder --chown=185 /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=builder --chown=185 /app/target/quarkus-app/*.jar /deployments/
COPY --from=builder --chown=185 /app/target/quarkus-app/app/ /deployments/app/
COPY --from=builder --chown=185 /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Xms128m -Xmx320m -XX:+UseSerialGC"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]