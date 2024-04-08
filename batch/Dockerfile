FROM openjdk:11-jdk-slim-stretch
VOLUME /tmp
COPY target/batch-0.0.1-SNAPSHOT.jar batch-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "batch-0.0.1-SNAPSHOT.jar"]