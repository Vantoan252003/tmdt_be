FROM eclipse-temurin:21-jre-alpine
WORKDIR /app/student
COPY student-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "student-0.0.1-SNAPSHOT.jar"]
