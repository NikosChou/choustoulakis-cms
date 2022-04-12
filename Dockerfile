FROM openjdk:17-alpine
COPY target/*.jar /app.jar
EXPOSE 9000
EXPOSE 8081
CMD ["java", "-jar", "/app.jar"]