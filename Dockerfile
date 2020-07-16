FROM openjdk:8-jdk-alpine

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY . /usr/app

RUN mvn clean install

CMD ["java", "-jar","/target/TicTacToe-1.0-SNAPSHOT.jar"]
