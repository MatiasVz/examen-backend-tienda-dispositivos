# Usar imagen base con Maven incluido
FROM maven:3.9.4-openjdk-17-slim AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa final con solo JRE
FROM openjdk:17-jre-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/target/pagina-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE $PORT

# Comando para ejecutar la aplicación
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
