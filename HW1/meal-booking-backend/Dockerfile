 FROM maven:3.9.4-eclipse-temurin-21 AS build
 WORKDIR /meal-booking-backend
 COPY pom.xml .
 RUN mvn dependency:go-offline
 COPY src src
 RUN mvn clean package -DskipTests

 FROM eclipse-temurin:21-jre-alpine
 WORKDIR /app
 COPY --from=build /meal-booking-backend/target/*.jar app.jar
 EXPOSE 3001
 CMD ["java", "-jar", "app.jar"]
