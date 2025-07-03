FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . /app

# Собираем весь мультимодульный проект
RUN mvn clean package -DskipTests

# Второй stage: только рантайм
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем итоговый JAR (замени путь и имя JAR при необходимости)
COPY --from=builder /app/device-service-app/target/device-service-app-0.0.1-SNAPSHOT.jar app.jar

CMD ["sh", "-c", "java -jar app.jar \
  --spring.datasource.url=$AIVEN_URI \
  --spring.datasource.username=$AIVEN_NAME \
  --spring.datasource.password=$AIVEN_PASS \
  --server.port=$PORT"]