# First stage: minimal runtime environment
FROM openjdk:11-jre-slim

# copy jar from the target
COPY backend/target/*.jar  /srv/app.jar

EXPOSE 8090

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/srv/app.jar"]


