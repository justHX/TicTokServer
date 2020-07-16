FROM openjdk:8-jdk-alpine

RUN mvn clean install

CMD ["java", "-jar","/target/TicTacToe-1.0-SNAPSHOT.jar"]
