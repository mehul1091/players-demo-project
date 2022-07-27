FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE
COPY target/${JAR_FILE} players-application.jar
ENTRYPOINT ["java","-jar","players-application.jar"]
