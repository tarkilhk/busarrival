FROM openjdk:8-jdk-alpine

EXPOSE 8778

VOLUME /busarrival
COPY target/*.jar /busarrival/busarrival.jar

ENTRYPOINT ["java","-jar","/busarrival/busarrival.jar"]