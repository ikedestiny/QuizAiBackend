# Stage 1: Build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper + pre-downloaded Gradle ZIP
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Now build (no download needed)
COPY src src
RUN ./gradlew build --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]