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

# FROM arm64v8/maven:3.9-amazoncorretto-21 AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests
# # Rename the jar to a known name
# RUN mv target/*.jar target/app.jar

# # Run stage
# FROM arm64v8/amazoncorretto:21
# WORKDIR /app
# COPY --from=build /app/target/app.jar app.jar

# EXPOSE 3001

# CMD ["java", "-jar", "-Dserver.port=3001", "app.jar"]