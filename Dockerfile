FROM openjdk:8-jdk
LABEL authors="calviniloki"
#ENV POSTGRES_PASSWORD postgres
#ENV REDIS_HOST redis
#ENV POSTGRES_HOST 172.17.0.2
ENV JWT_SECRET ccf9943ba5bf9f3686e0997e510c01956f08c48c8aa99c504589bf62e87f671e
RUN mkdir -p /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
