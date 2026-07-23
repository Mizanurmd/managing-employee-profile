FROM eclipse-temurin:21.0.11_10-jdk-jammy
COPY target/employeeManagement-0.0.1-SNAPSHOT.jar employeeManagement.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "employeeManagement.jar"]
