FROM openjdk
WORKDIR todo
ADD target/todo-1.0.jar app.jar
ENTRYPOINT java -jar app.jar