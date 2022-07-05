FROM openjdk:8-jre-slim
COPY build/libs/onlinevacationsMicroService-0.1.0.jar /
WORKDIR /
CMD ["java", "-jar", "onlinevacationsMicroService-0.1.0.jar"]
