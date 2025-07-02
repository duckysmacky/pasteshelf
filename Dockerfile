FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/*.jar pasteshelf.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pasteshelf.jar"]