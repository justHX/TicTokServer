FROM openjdk:11
EXPOSE 8888
ADD target/tic-tac-server.jar tic-tac-server.jar
CMD ["java", "-jar","tic-tac-server.jar"]
