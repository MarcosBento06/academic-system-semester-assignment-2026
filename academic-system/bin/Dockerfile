# ── Stage 1: build ──────────────────────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# copia somente o pom primeiro para aproveitar o cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -q

# copia o restante e compila
COPY src ./src
RUN mvn package -DskipTests -q

# ── Stage 2: runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# copia o fat-jar gerado no estágio de build
COPY --from=build /app/target/academic-system-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# copia o arquivo de usuários para dentro do container
COPY usuarios.txt .

# a aplicação lê stdin → modo interativo obrigatório (-it no docker run)
ENTRYPOINT ["java", "-jar", "app.jar"]
