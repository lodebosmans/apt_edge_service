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

    private String http = "http://";

    @GetMapping("/statistics/user/{userName}")
    public List<UserStatistics> getStatisticsByUserName(@PathVariable String userName){
        List<UserStatistics> returnList= new ArrayList<>();
        ResponseEntity<List<Scan>> responseEntityScans =
                restTemplate.exchange(http + scanServiceBaseUrl + "/scans/user/{userName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Scan>>() {
                        }, userName);
        List<Scan> scans = responseEntityScans.getBody();
        for (Scan scan:
                scans) {
            Car car =
                    restTemplate.getForObject(http + carServiceBaseUrl + "/cars/{car}",
                            Car.class, scan.getCarBrand());
            returnList.add(new UserStatistics(car, scan));
        }
        return returnList;
    }

    @GetMapping("/statistics/car/{carBrand}")
    public UserStatistics getStatisticsByCarBrand(@PathVariable String carBrand){
        Car car =
                restTemplate.getForObject(http + carServiceBaseUrl + "/cars/{carBrand}",
                        Car.class, carBrand);
        ResponseEntity<List<Scan>> responseEntityScans =
                restTemplate.exchange(http + scanServiceBaseUrl + "/scans/{car}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Scan>>() {
                        }, carBrand);
        return new UserStatistics(car,responseEntityScans.getBody());
    }

    @GetMapping("/statistics/{userName}/car/{carBrand}")
    public UserStatistics getStatisticsByUserNameAndCarBrand(@PathVariable String userName, @PathVariable String carBrand){
        Car car =
                restTemplate.getForObject(http + carServiceBaseUrl + "/cars/{carBrand}",
                        Car.class, carBrand);
        Scan scan =
                restTemplate.getForObject(http + scanServiceBaseUrl + "/scans/user/{userName}/car/{carBrand}",
                        Scan.class, userName, carBrand);
        return new UserStatistics(car, scan);
    }

    @GetMapping("/statistics/cars")
    public List<Car> getCarBrands(){
        ResponseEntity<List<Car>> responseEntityCars =
                restTemplate.exchange(http + carServiceBaseUrl + "/cars",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {}
                );
        return responseEntityCars.getBody();
    }


    @PostMapping("/statistics")
    public UserStatistics addStatistics(@RequestParam String userName, @RequestParam String carBrand, @RequestParam Integer scoreNumber){
        // First check if a scan for this combination already exists, if so redirect to PUT command instead of performing the POST
        Scan scanTemp =
                restTemplate.getForObject(http + scanServiceBaseUrl + "/scans/user/{userName}/car/{carBrand}",
                        Scan.class, userName, carBrand);
        if (scanTemp != null) {
            // The scan combination already exists
            // Redirect to the PUT method and get the UserStatistics object back
            UserStatistics updatedUserStatistics = updateStatistics(userName, carBrand, scoreNumber);
            return updatedUserStatistics;
        } else {
            // The combination does not exist yet, so POST that combination.
            Scan scan =
                    restTemplate.postForObject(http + scanServiceBaseUrl + "/scans",
                            new Scan(userName, carBrand, scoreNumber), Scan.class);
            Car car =
                    restTemplate.getForObject(http + carServiceBaseUrl + "/cars/{carBrand}",
                            Car.class, carBrand);
            return new UserStatistics(car, scan);
        }
    }

    @PutMapping("/statistics")
    public UserStatistics updateStatistics(@RequestParam String userName, @RequestParam String carBrand, @RequestParam Integer scoreNumber){
        Scan scan =
                restTemplate.getForObject(http + scanServiceBaseUrl + "/scans/user/{userName}/car/{carBrand}",
                        Scan.class, userName, carBrand);
        if (scan == null) {
            // The scan does not exist yet.
            // Redirect to the POST method
            UserStatistics postedUserStatistics = addStatistics(userName, carBrand, scoreNumber);
            return postedUserStatistics;
        } else {
            scan.setScoreNumber(scoreNumber);
            ResponseEntity<Scan> responseEntityReview =
                    restTemplate.exchange(http + scanServiceBaseUrl + "/scans",
                            HttpMethod.PUT, new HttpEntity<>(scan), Scan.class);
            Scan retrievedScan = responseEntityReview.getBody();
            Car car =
                    restTemplate.getForObject(http + carServiceBaseUrl + "/cars/{carBrand}",
                            Car.class, carBrand);
            return new UserStatistics(car, retrievedScan);
        }
    }

    @DeleteMapping("/statistics/{userName}/car/{carBrand}")
    public <T> ResponseEntity<T> deleteStatistics(@PathVariable String userName, @PathVariable String carBrand){

        restTemplate.delete(http + scanServiceBaseUrl + "/scans/user/{userName}/car/{CarBrand}", userName, carBrand);

        return ResponseEntity.ok().build();
    }


}
