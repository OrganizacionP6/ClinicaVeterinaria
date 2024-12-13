# syntax=docker/dockerfile:1

################################################################################
# Etapa 1: Descargar dependencias
################################################################################
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copiar el wrapper de Maven y configuración
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Descargar dependencias para aprovechar el caché de Docker
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

################################################################################
# Etapa 2: Construir el proyecto
################################################################################
FROM deps as package

WORKDIR /build

# Copiar el código fuente de la aplicación
COPY ./src src/

# Construir el proyecto y generar el archivo JAR
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
# Etapa 3: Optimizar capas con Spring Boot Layer Tools
################################################################################
FROM package as extract

WORKDIR /build

# Descomponer el JAR en capas optimizadas
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################
# Etapa 4: Ejecutar la aplicación
################################################################################
FROM eclipse-temurin:21-jre-jammy AS final

# Crear un usuario no privilegiado
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

# Copiar las capas extraídas desde la etapa anterior
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

# Exponer el puerto 8080
EXPOSE 8080

# Comando de inicio para ejecutar la aplicación
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
