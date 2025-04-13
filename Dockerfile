# Use a minimal OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy all files into the container
COPY . .

# Make mvnw executable (in case it's not)
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean install -DskipTests

# Expose the port used by the application
EXPOSE 8080

# Run the jar file (adjust version if needed)
CMD ["java", "-jar", "target/expense-0.0.1-SNAPSHOT.jar"]
