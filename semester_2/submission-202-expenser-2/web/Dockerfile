# Just basing the image on an existing image named ubuntu and tagged with 21.04.
# If the image is not found locally, it will be automatically downloaded from Docker Hub.
FROM ubuntu:21.04     

# Update and Install the Java Runtime Environment for version 11.
# -y silence the installation, because there won't be anyone to monitor
RUN apt-get update
RUN apt-get install -y openjdk-11-jre

# Copy our JAR into the image as /srv path
ADD target/web-*.jar /srv

WORKDIR /srv
EXPOSE 8080

CMD java -jar web-1.0.1.jar
