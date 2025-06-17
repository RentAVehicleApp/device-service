FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "device-service-app/target/device-service-app-0.0.1-SNAPSHOT.jar"]
