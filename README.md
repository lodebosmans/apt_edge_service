
# Advanced Programming topics
## _By far the greatest, most scalable and performant backend architecture. Ever._

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
- ✨a lot of API magic ✨ 
- Okteto cloud hosting

The backend microservices provide information about car properties (relation MySQL database) and scans of cars (MongoDB).

## Links to the relevant github repositories
- [Edge service](https://github.com/lodebosmans/apt_edge_service)
- [Car service](https://github.com/lodebosmans/apt_car_service)
- [Scan service](https://github.com/lodebosmans/apt_scan_service)
- [Docker compose](https://github.com/lodebosmans/apt_docker_compose)

## Postman 
Below we will test all edge service endpoints with Postman. All endpoints are tested on the Okteto hosted environment.

### GET /statistics/user/{userName}
This GET enpoint will collect all scans performed by {userName}.Try it out yourself by right clicking [this link](https://apt-edge-service-lodebosmans.cloud.okteto.net/statistics/user/Lode) and opening it in a new tab!

![alt text](https://github.com/lodebosmans/apt_edge_service/blob/main/img/Get1.png?raw=true)


# DTO niet vergeten