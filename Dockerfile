FROM openjdk:17
WORKDIR /app
COPY /target/demo1.jar /app
ENTRYPOINT ["java", "jar", "demo1.jar"]