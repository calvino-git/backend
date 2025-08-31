FROM openjdk:17-jdk
LABEL authors="calviniloki"
ENV POSTGRES_PASSWORD postgres
ENV JWT_SECRET treduytjakfnkl146324765ntifbFIYUTge
RUN mkdir -p /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
