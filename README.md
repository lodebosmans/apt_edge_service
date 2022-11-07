# Advanced Programming topics
## _By far the greatest, most scalable and performant backend architecture. Ever._

In this _fabulous_ project we developed a Java backend architecture to support a frontend Flutter application. The architecture includes:
- Three microservices
  - Two dockerized Java backend microservices
    - One relational database (MySQL)
    - One NoSQL database (MonogDB)
  - One dockerized Java edge service connecting to the two backend microservices
- Github Actions pipeline, including Jar artifact and dockerhub connection
- ✨API magic ✨ 

The microservices provide information about car properties (relation MySQL database) and scans of cars (MongoDB).

## Links to the relevant github repositories
- [Edge service](https://github.com/lodebosmans/apt_edge_service)
- [Car service](https://github.com/lodebosmans/apt_car_service)
- [Scan service](https://github.com/lodebosmans/apt_scan_service)
- [Docker compose](https://github.com/lodebosmans/apt_docker_compose)

##### DTO niet vergeten