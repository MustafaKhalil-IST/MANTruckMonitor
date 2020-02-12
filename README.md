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

# CI/CD
Using github Actions.

# How does it work?
You can search for a Truck by writing its plate license then clicking `#apply`, you can also search for restaurants, gas stations, hotels or all of it, by selecting from the dropdown meny `Looking for`, you can specify the search diameter in meters by writing in the `distance` input box, the button `add car` is to create a truck, you will have to enter its plate license to an alert box, then you can update its locations manually by putting the license plate in the plate input box and just clicking on the map.
Icons are for the searched points of interest, by clicking on it, a green line will be drawn from the current location of the truck to it, and by clicking on the line you will get the distance.
