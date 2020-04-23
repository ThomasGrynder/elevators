# Elevator Shaft

An elevator controller!

## Backend

Elevator Shaft symulator build on top of WebFlux (reactive stack with event loop concurrency model).

### Build And Run

Spring app can be started as seen below.

build and run the code with Maven

    mvn package
    mvn spring-boot:run

or start the target JAR file 

    mvn package
    java -jar target/elevators-backend-0.0.1-SNAPSHOT.jar

## Frontend

Simple UI for Elevator Shaft service. Created with usage of Angular and NgRx Store. Start it, open in several browser tabs and play around.

### Build And Run

Installing dependencies:

    npm install

Running app:

    npm start
