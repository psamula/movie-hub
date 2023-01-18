FROM openjdk:18-alpine
COPY . /app
WORKDIR /app
CMD ["java", "-jar", "target/movie-hub-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

