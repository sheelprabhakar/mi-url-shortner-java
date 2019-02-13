FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888 8084 8082 8761
ARG JAR_FILE
COPY build/libs/url-shortner-1.0.jar c4c/url-shortner-1.0.jar
ENTRYPOINT ["java","-jar","c4c/url-shortner-1.0.jar"]
