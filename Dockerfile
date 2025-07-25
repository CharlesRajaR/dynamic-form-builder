FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/form-builder-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]