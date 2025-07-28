# Usar OpenJDK 17 como imagen base
FROM openjdk:17-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn

# Copiar el archivo pom.xml
COPY pom.xml .

# Descargar dependencias
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto
EXPOSE $PORT

# Comando para ejecutar la aplicación
CMD ["java", "-Dserver.port=${PORT}", "-jar", "target/pagina-0.0.1-SNAPSHOT.jar"]
