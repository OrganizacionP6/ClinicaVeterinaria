# Usar una imagen base de JDK para construir el proyecto
FROM eclipse-temurin:21-jdk AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos necesarios para el proyecto
COPY mvnw* ./
COPY .mvn .mvn
COPY pom.xml .

# Descargar las dependencias
RUN ./mvnw dependency:resolve

# Copiar el código fuente del backend desde "src"
COPY src ./src

# Construir el proyecto
RUN ./mvnw package -DskipTests

# Crear una nueva imagen con el runtime necesario
FROM eclipse-temurin:21-jre

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto para el backend
EXPOSE 8080

# Comando para iniciar el backend
ENTRYPOINT ["java", "-jar", "app.jar"]
