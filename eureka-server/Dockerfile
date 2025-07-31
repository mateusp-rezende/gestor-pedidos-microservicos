# --- Estágio 1: Build com Maven ---
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# --- Estágio 2: Imagem Final ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copia o .jar correto para este serviço
COPY --from=builder /app/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
# Expõe a porta correta
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]