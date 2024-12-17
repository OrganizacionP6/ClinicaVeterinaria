FROM ubuntu:latest
LABEL authors="paloma"

# Usar una imagen oficial
FROM amazoncorretto:21-alpine-jdk

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/*.jar app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
