FROM openjdk:17-jdk-slim
WORKDIR /app
COPY /target/demo1.jar /app
ENTRYPOINT ["java", "jar", "demo1.jar"]