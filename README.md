# MANTruckMonitor
![Node.js CI](https://github.com/MustafaKhalil-IST/MANTruckMonitor/workflows/Node.js%20CI/badge.svg)
![.github/workflows/maven.yml](https://github.com/MustafaKhalil-IST/MANTruckMonitor/workflows/.github/workflows/maven.yml/badge.svg)

# Introduction
MAN Truck Monitor is an application to help trucks manager find points of interests nearby, it is built with Spring-boot and React.
![Image](https://github.com/MustafaKhalil-IST/MANTruckMonitor/blob/master/snip.PNG)
# Requirements
  - Java 1.8*
  - Node.js
  - Postgresql
  - Maven
  
# How to Run?
  1. Backend: to run the server
```sh
$ cd TruckMonitor/backend/backend
$ mvn clean spring-boot:run
```
Now the server is accessible on http://localhost:8080

  2. Frontend: 
```sh
$ cd TruckMonitor/frontend/frontend
$ npm start
```
Now you can access the app on http://localhost:3000

# Test

```sh
$ cd TruckMonitor/backend/backend
$ mvn clean test
```

# API Documentation
Using Swagger: http://localhost:8080/swagger-ui.html
