FROM openjdk:11
EXPOSE 8072
ADD target/microservicio-movement.jar microservicio-movement.jar
ENTRYPOINT ["java","-jar","/microservicio-movement.jar"]