FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/aston-1.0-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]