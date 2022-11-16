# Advanced Programming topics

_"By far the greatest, most scalable and performant backend architecture in the history of Thomas More 4WT advanced programming topics teams sitting on the first row in class. Ever."_

## Contents
- [Introduction](#introduction)
- [Diagram microservice architecture](#diagram-microservice-architecture)
- [Relevant github repositories](#relevant-github-repositories)
- [CI-CD pipeline](#ci-cd-pipeline)
- [Postman](#postman)
- [Swagger UI](#swagger-ui)
- [Code coverage](#code-coverage)
- [GitHub Actions](#github-actions)
- [SonarCloud](#sonar-cloud)
- [Docker Hub](#docker-hub)
- [Okteto cloud hosting](#okteto-cloud-hosting)
- [Specials](#specials)


## Introduction
In this _fabulous_ project we developed a Java backend architecture to support a Flutter frontend application. The architecture includes:
- Three microservices
  - Two dockerized Java backend microservices
    - One relational database (MySQL)
    - One NoSQL database (MonogDB)
  - One dockerized Java edge service 
    - Connects to the two backend microservices
    - Seven API endpoints
      - Four GET endpoints
      - One POST endpoint
      - One PUT endpoint
      - One DEL endpoint
- Github Actions pipeline, including Jar artifact and dockerhub connection
- ✨A lot of API magic ✨ 
- Okteto cloud hosting

The backend microservices provide information about car properties (relation MySQL database) and scans of cars (MongoDB).

## Diagram microservice architecture
Our backend applications has the following architecture.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/BackEndArchitecture.drawio.png?raw=true)

## Relevant github repositories
- [Edge service](https://github.com/lodebosmans/apt_edge_service)
- [Car service](https://github.com/lodebosmans/apt_car_service)
- [Scan service](https://github.com/lodebosmans/apt_scan_service)
- [Docker compose](https://github.com/lodebosmans/apt_docker_compose)

## CI-CD pipeline

### GitHub workflow
All code and version control is handled by GitHub. A GitHub workflow is defined including the code submission to Sonar Cloud and Docker Hub. Finally, the creation of a JAR artifact file is included as well.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/GitHubActions.png?raw=true)

### Sonar Cloud
The entire architecture and code was analyzed by Sonar Cloud. For all initially signaled issues, we provided a fix or code refactoring. Proudly we can state that there are no bugs or vulnerabilites over the three entire microservices.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Sonar.png?raw=true)

However, we do keep two code smells in the edge service. We deliberately chose to keep these smells for the sake of code readability. The smells suggests to replace a duplicate string that occurs multiple times. However, this duplicate code is a part of our API pathways. Replacing this with a fixed variable would impair the readability of the code and the endpoint pathway. Therefore, we opted to keep this as is.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/SonarSmell.png?raw=true)

### Docker Hub
The application is dockerized into containers that are hosted on Docker Hub.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/DockerHub.png?raw=true)

### Okteto cloud hosting

The dockerized containers are finally deployed on Okteto cloud, providing the final API endpoints.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Okteto.png?raw=true)

## Postman 
Below we will test all edge service endpoints with Postman. All endpoints are tested on the Okteto hosted environment. And guess what? You can try the GET requests yourself as well!

### GET /statistics/user/{userName}
This GET enpoint will collect all scans performed by {userName}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/user/lode) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get1.png?raw=true)

### GET /statistics/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/car/traktor) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get2.png?raw=true)

### GET /statistics/{userName}/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand} performed by a specific {userName}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/lode/car/traktor) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get3.png?raw=true)

### GET /statistics/cars
This GET enpoint will collect all cars available in the relational database.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/cars) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get4.png?raw=true)

### POST /statistics?userName={userName}&carBrand={carBrand}&scoreNumber={scoreNumber}
This POST enpoint will post a new scan of {carBrand} by {userName} with a score of {scoreNumber}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Post.png?raw=true)

### PUT /statistics?userName={userName}&carBrand={carBrand}&scoreNumber={scoreNumber}
This PUT enpoint will update an existing scan of {carBrand} by {userName} with a new score of {scoreNumber}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Put.png?raw=true)

### DEL /statistics/{userName}/car/{carBrand}
This DEL enpoint will demete an existing scan of {carBrand} by {userName}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Del.png?raw=true)

## Swagger UI
Below we will test all edge service endpoints with the Swagger UI. All endpoints are tested on the Okteto hosted environment. And guess what? You can try the ALL the requests yourself as well (including the POST, PUT and DEL endpoints)! Right click [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/swagger-ui.html#/user-statistics-controller) and open the Swagger UI in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/SwaggerUI.png?raw=true)

### GET /statistics/user/{userName}
This GET enpoint will collect all scans performed by {userName}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get1_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get1_Swagger_B.png?raw=true)

### GET /statistics/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get2_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get2_Swagger_B.png?raw=true)

### GET /statistics/{userName}/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand} performed by a specific {userName}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get3_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get3_Swagger_B.png?raw=true)

### GET /statistics/cars
This GET enpoint will collect all cars available in the relational database.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get4_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get4_Swagger_B.png?raw=true)

### POST /statistics?userName={userName}&carBrand={carBrand}&scoreNumber={scoreNumber}
This POST enpoint will post a new scan of {carBrand} by {userName} with a score of {scoreNumber}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Post_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Post_Swagger_B.png?raw=true)

### PUT /statistics?userName={userName}&carBrand={carBrand}&scoreNumber={scoreNumber}
This PUT enpoint will update an existing scan of {carBrand} by {userName} with a new score of {scoreNumber}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Put_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Put_Swagger_B.png?raw=true)

### DEL /statistics/{userName}/car/{carBrand}
This DEL enpoint will demete an existing scan of {carBrand} by {userName}.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Del_Swagger_A.png?raw=true)
![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Del_Swagger_B.png?raw=true)

## Code coverage
In this section, we will provide code coverage of all microservice controllers and methods.

### Car microservice (MySQL)
#### Unit testing
The image below provides a 100% coverage for all classes and methods in the controller, model and repository.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/CarControllerUnitTesting.png?raw=true)
#### Integration testing
The image below provides a 100% coverage for all classes and methods in the controller, model and repository.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/CarControllerIntegrationTesting.png?raw=true)

We do notice on only 45% of the lines are covered for the CarController. This is linked to the PostConstruct method that is not fully used, as there is already a database available.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/CarControllerIntegrationTesting_Lines.png?raw=true)
### Scan microservice (MongoDB)
#### Unit testing
The image below provides a 100% coverage for all classes and methods in the controller, model and repository.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/ScanControllerUnitTesting.png?raw=true)
#### Integration testing
The image below provides a 100% coverage for all classes and methods in the controller, model and repository.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/ScanControllerIntegrationTesting.png?raw=true)

We do notice on only 82% of the lines are covered for the ScanController. This is linked to the PostConstruct method that is not fully used, as there is already a database available.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/ScanControllerIntegrationTesting_Lines.png?raw=true)
### Edge microservice
#### Unit testing
The image below provides a 100% coverage for all classes and methods in the controller, model and repository.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/EdgeControllerUnitTesting.png?raw=true)

## Specials
### Data Transfer Object (DTO)
In our project, we included a Data Transfer Object into two functions to protect the application against malicious injections. For all POST and PUT mappings, the DTO was implemented. Below you can see an example of the ScanDTO object class, which resembles the Scan class.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/ScanDTO.png?raw=true)

In the POST method, this ScanDTO object is received by the addScan function. Within this function, the information is collected from the ScanDTO object and injected in an object from the Scan class. Finally, the Scan object is stored.

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/ScanDTO_Post.png?raw=true)

# donatie toevoegen