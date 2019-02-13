FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888 8083 8082 8761
ARG JAR_FILE
COPY build/libs/account-service-1.0.jar c4c/account-service-1.0.jar
ENTRYPOINT ["java","-jar","c4c/account-service-1.0.jar"]
