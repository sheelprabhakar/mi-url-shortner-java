FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888 8083 8082 8761
ARG JAR_FILE
COPY build/libs/configure-service-0.0.1-SNAPSHOT.jar c4c/configure-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","c4c/configure-service-0.0.1-SNAPSHOT.jar"]
