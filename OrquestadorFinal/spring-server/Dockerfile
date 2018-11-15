FROM openjdk:8-jdk-alpine
EXPOSE 5001
ADD /target/orquestando.jar orquestando.jar
ENTRYPOINT ["java","-jar","orquestando.jar"]