#builder
FROM maven:3-jdk-8 AS builder
WORKDIR /project
COPY orders_management .
RUN mvn package


FROM openjdk:8-jdk-alpine

#Informa la rete su quale porta si collegherà il container
EXPOSE 8088

WORKDIR /app
#viene copiato l'artefatto costruito dal builder nell'immagine docker
COPY --from=builder /project/target/orders_management-0.0.1-SNAPSHOT.jar ./orders_management.jar
CMD java -jar orders_management.jar