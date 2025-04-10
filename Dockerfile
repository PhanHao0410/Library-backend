# Use a base image with Java and ca-certificates
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/library-0.0.1-SNAPSHOT.jar app.jar

# (Optional) Cài thêm ca-certificates nếu chưa có
RUN apt-get update && apt-get install -y ca-certificates

# Run the jar
CMD ["java", "-jar", "app.jar"]