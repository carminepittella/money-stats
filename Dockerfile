# Stage 1: Build dell'applicazione con Maven e JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Pre-download delle dipendenze per velocizzare i build successivi
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Compilazione dei sorgenti
COPY src ./src
RUN mvn package -DskipTests -B

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

CMD ["java", "-Xms128m", "-Xmx320m", "-Dquarkus.http.host=0.0.0.0", "-jar", "/deployments/quarkus-run.jar"]