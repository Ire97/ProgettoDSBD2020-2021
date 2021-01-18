#builder
FROM maven:3-jdk-8 AS builder
WORKDIR /project
COPY fake_producer .
RUN mvn package

FROM openjdk:8-jdk-alpine
#Informa la rete su quale porta si collegherà il container
EXPOSE 8089
WORKDIR /app
#viene copiato l'artefatto costruito dal builder nell'immagine docker
COPY --from=builder /project/target/fake_producer-0.0.1-SNAPSHOT.jar ./fake_producer.jar
CMD java -jar fake_producer.jar