# Advanced Programming topics
## _"By far the greatest, most scalable and performant backend architecture. Ever."_

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

## Links to the relevant github repositories
- [Edge service](https://github.com/lodebosmans/apt_edge_service)
- [Car service](https://github.com/lodebosmans/apt_car_service)
- [Scan service](https://github.com/lodebosmans/apt_scan_service)
- [Docker compose](https://github.com/lodebosmans/apt_docker_compose)

## Postman 
Below we will test all edge service endpoints with Postman. All endpoints are tested on the Okteto hosted environment. And guess what? You can try the GET requests yourself as well!

### GET /statistics/user/{userName}
This GET enpoint will collect all scans performed by {userName}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/user/Lode) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get1.png?raw=true)

### GET /statistics/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/car/Traktor) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get2.png?raw=true)

### GET /statistics/{userName}/car/{carBrand}
This GET enpoint will collect all scans taken of a specific {carBrand} performed by a specific {userName}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/Lode/car/Traktor) and opening it in a new tab!

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

## Specials
### DTO niet vergeten
# donatie toevoegen