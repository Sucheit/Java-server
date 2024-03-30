FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Copy the compiled Java Spring Boot application JAR file into the container
COPY target/*.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Specify the command to run the Java Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]