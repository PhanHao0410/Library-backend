FROM openjdk:17-jdk-slim

# Cài chứng chỉ SSL để kết nối MongoDB Atlas
RUN apt-get update && apt-get install -y ca-certificates

WORKDIR /app
COPY --from=build /app/target/library-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
