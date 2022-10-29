package com.example.edgeservice.controller;

import com.example.edgeservice.model.Car;
import com.example.edgeservice.model.Scan;
import com.example.edgeservice.model.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserStatisticsController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${scanservice.baseurl}")
    private String scanServiceBaseUrl;

    @Value("${carservice.baseurl}")
    private String carServiceBaseUrl;

    @GetMapping("/statistics/user/{userName}")
    public List<UserStatistics> getStatisticsByUserName(@PathVariable String userName){
        List<UserStatistics> returnList= new ArrayList();
        ResponseEntity<List<Scan>> responseEntityScans =
                restTemplate.exchange("http://" + scanServiceBaseUrl + "/scans/user/{userName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Scan>>() {
                        }, userName);
        List<Scan> scans = responseEntityScans.getBody();
        for (Scan scan:
                scans) {
            Car car =
                    restTemplate.getForObject("http://" + carServiceBaseUrl + "/cars/{car}",
                            Car.class, scan.getCarBrand());
            returnList.add(new UserStatistics(car, scan));
        }
        return returnList;
    }

    @GetMapping("/statistics/car/{carBrand}")
    public UserStatistics getStatisticsByCarBrand(@PathVariable String carBrand){
        Car car =
                restTemplate.getForObject("http://" + carServiceBaseUrl + "/cars/{carBrand}",
                        Car.class, carBrand);
        ResponseEntity<List<Scan>> responseEntityScans =
                restTemplate.exchange("http://" + scanServiceBaseUrl + "/scans/{car}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Scan>>() {
                        }, carBrand);
        return new UserStatistics(car,responseEntityScans.getBody());
    }

    @GetMapping("/statistics/{userName}/car/{carBrand}")
    public UserStatistics getStatisticsByUserNameAndCarBrand(@PathVariable String userName, @PathVariable String carBrand){
        Car car =
                restTemplate.getForObject("http://" + carServiceBaseUrl + "/cars/{carBrand}",
                        Car.class, carBrand);
        Scan scan =
                restTemplate.getForObject("http://" + scanServiceBaseUrl + "/scans/user/" + userName + "/car/" + carBrand,
                        Scan.class);
        return new UserStatistics(car, scan);
    }

    @GetMapping("/statistics/cars")
    public List<Car> getCarBrands(){
        List<Car> cars = new ArrayList();
        ResponseEntity<List<Car>> responseEntityCars =
                restTemplate.exchange("http://" + carServiceBaseUrl + "/cars",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {}
                );
        cars = responseEntityCars.getBody();
        return cars;
    }


    @PostMapping("/statistics")
    public UserStatistics addStatistics(@RequestParam String userName, @RequestParam String carBrand, @RequestParam Integer scoreNumber){
        Scan scan =
                restTemplate.postForObject("http://" + scanServiceBaseUrl + "/scans",
                        new Scan(userName,carBrand,scoreNumber),Scan.class);
        Car car =
                restTemplate.getForObject("http://" + carServiceBaseUrl + "/cars/{carBrand}",
                        Car.class,carBrand);
        return new UserStatistics(car, scan);
    }

    @PutMapping("/statistics")
    public UserStatistics updateStatistics(@RequestParam String userName, @RequestParam String carBrand, @RequestParam Integer scoreNumber){
        Scan scan =
                restTemplate.getForObject("http://" + scanServiceBaseUrl + "/scans/user/" + userName + "/car/" + carBrand,
                        Scan.class);
        if (scan == null) {
            // Return an empty object
            return new UserStatistics();
        } else {
            scan.setScoreNumber(scoreNumber);
            ResponseEntity<Scan> responseEntityReview =
                    restTemplate.exchange("http://" + scanServiceBaseUrl + "/scans",
                            HttpMethod.PUT, new HttpEntity<>(scan), Scan.class);
            Scan retrievedScan = responseEntityReview.getBody();
            Car car =
                    restTemplate.getForObject("http://" + carServiceBaseUrl + "/cars/{carBrand}",
                            Car.class, carBrand);
            return new UserStatistics(car, retrievedScan);
        }
    }

    @DeleteMapping("/statistics/{userName}/car/{carBrand}")
    public ResponseEntity deleteStatistics(@PathVariable String userName, @PathVariable String carBrand){

        restTemplate.delete("http://" + scanServiceBaseUrl + "/scans/user/" + userName + "/car/" + carBrand);

        return ResponseEntity.ok().build();
    }


}
