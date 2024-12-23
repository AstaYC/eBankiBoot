# Use the official Jenkins image as the base
FROM jenkins/jenkins:lts

# Switch to root user to install software
USER root

# Install JDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean

# Set JAVA_HOME environment variable for JDK 17
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Install Maven 3.8.8
RUN curl -fsSL https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz -o /tmp/maven.tar.gz && \
    tar -xzf /tmp/maven.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.8 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm /tmp/maven.tar.gz

# Set Maven environment variables
ENV MAVEN_HOME=/opt/maven
ENV PATH="$MAVEN_HOME/bin:$PATH"

# Return to Jenkins user
USER jenkins

FROM maven:3.8-openjdk-17-slim as builder

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package -DskipTests

Use the official OpenJDK 17 runtime image for running the app
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/eBankify-0.0.1-SNAPSHOT.war /app/app.jar

EXPOSE 8082

Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]