# OpenJDK JRE
FROM openjdk:21-jdk-slim

WORKDIR /opt/app

ARG JAR_FILE=ms-currency-exchange-v1-0.0.1-SNAPSHOT.jar

EXPOSE 9006

# Copy JAR file
COPY ${JAR_FILE} ms-currency-exchange-v1-0.0.1-SNAPSHOT.jar

# Run the app
ENTRYPOINT ["java", "-jar", "ms-currency-exchange-v1-0.0.1-SNAPSHOT.jar", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-browser"]
