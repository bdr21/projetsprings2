FROM openjdk:11

WORKDIR /usr/src/name-of-app


ADD target/*.jar app-backend.jar

CMD ["java", "-jar", "app-backend.jar"]

