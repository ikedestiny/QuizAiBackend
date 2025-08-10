# Stage 1: Build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# 1. First copy only the files needed for dependency resolution
COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY build.gradle .
COPY settings.gradle .

# 2. Download dependencies (this layer will be cached unless build.gradle changes)
RUN ./gradlew dependencies --no-daemon

# 3. Now copy the rest of the source code
COPY src src

# 4. Build the application
RUN ./gradlew build --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]