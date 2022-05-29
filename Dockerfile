# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:11.0.15-jre-slim-buster
# copy the packaged jar file into our docker image
COPY target/dixit-0.0.1-SNAPSHOT.jar /app.jar
# set the startup command to execute the jar
CMD ["java", "-jar", "/app.jar"]
