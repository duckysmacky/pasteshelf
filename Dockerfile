# Build the app
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Copy files to build
COPY src/ src/
COPY gradle/ gradle/
COPY gradlew/ .
COPY build.gradle .
COPY settings.gradle .

# Build (skip tests)
RUN ./gradlew build -x test

# Run the app
FROM eclipse-temurin:21-jre-jammy AS runner
WORKDIR /app

# Copy the built runnable jar
COPY --from=builder /app/build/libs/*.jar pasteshelf.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pasteshelf.jar"]