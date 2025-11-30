# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
COPY --from=builder /app/target/herkat-0.0.1.jar /herkat.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/herkat.jar"]