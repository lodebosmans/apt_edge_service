package com.example.edgeservice;

import com.example.edgeservice.model.Car;
import com.example.edgeservice.model.Scan;
import com.example.edgeservice.model.UserScore;
import com.example.edgeservice.model.UserStatistics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserStatisticsControllerUnitTests {

    @Value("${scanservice.baseurl}")
    private String scanServiceBaseUrl;

    @Value("${carservice.baseurl}")
    private String carServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Car car1 = new Car("Audi",200,5);
    private Car car2 = new Car("Tesla",150,1);
    private Car car3 = new Car("Ferarri",300,2);
    private List<Car> allCars = Arrays.asList(car1, car2);

    private Scan scanUser1Car1 = new Scan("Lode", "Audi", 5);
    private Scan scanUser1Car2 = new Scan("Lode", "Tesla", 3);
    private Scan scanUser2Car1 = new Scan("Johnny", "Audi", 4);

    private List<Scan> allScansFromUser1 = Arrays.asList(scanUser1Car1, scanUser1Car2);
    private List<Scan> allScansForCar1 = Arrays.asList(scanUser1Car1,scanUser2Car1);
    private List<Scan> allScansForCar2 = Arrays.asList(scanUser1Car2);

    private UserStatistics userStatisticsCar1 = new UserStatistics(car1, allScansForCar1);


    @BeforeEach
    public void initializeMockserver() throws JsonProcessingException, URISyntaxException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    // Eerste test
    @Test
    void whenGetStatisticsByUserName_thenReturnScansJson() throws Exception {

        // GET all scans from User 1
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allScansFromUser1))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        // GET Car 2 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/tesla")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car2))
                );

        mockMvc.perform(get("/statistics/user/{userName}", "lode"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].carBrand", is("audi")))
                .andExpect(jsonPath("$[0].userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$[0].userScores[0].scoreNumber", is(5)))
                .andExpect(jsonPath("$[1].carBrand", is("tesla")))
                .andExpect(jsonPath("$[1].userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$[1].userScores[0].scoreNumber", is(3)));
    }

    // Tweede test
    @Test
    void whenGetStatisticsByCarBrand_thenReturnStatisticsJson() throws Exception {

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        // GET all scans for Car 1
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allScansForCar1))
                );

        mockMvc.perform(get("/statistics/car/{carBrand}", "audi"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("audi")))
                .andExpect(jsonPath("$.userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(5)))
                .andExpect(jsonPath("$.userScores[1].userName", is("johnny")))
                .andExpect(jsonPath("$.userScores[1].scoreNumber", is(4)));

    }


    // Derde test
    @Test
    void whenGetUserStatisticsForUserAndCarBrand_thenReturnUserStatisticsForUserAndCarBrandJson() throws Exception {

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode/car/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(scanUser1Car1))
                );

        mockMvc.perform(get("/statistics/{userName}/car/{carBrand}","lode", "audi"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("audi")))
                .andExpect(jsonPath("$.userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(5)));

    }


    // Vierde test
    @Test
    void whenGetAllCars_thenReturnAllCarsJson() throws Exception {

        // GET all cars info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allCars))
                );

        mockMvc.perform(get("/statistics/cars"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].carBrand", is("audi")))
                .andExpect(jsonPath("$[0].maxSpeed", is(200)))
                .andExpect(jsonPath("$[0].numberOfSeats", is(5)))
                .andExpect(jsonPath("$[1].carBrand", is("tesla")))
                .andExpect(jsonPath("$[1].maxSpeed", is(150)))
                .andExpect(jsonPath("$[1].numberOfSeats", is(1)));

    }



    // Vijfde test A: POST echte nieuwe scan = POST
    @Test
    void whenPostStatitics_thenReturnPostedStatisticsJson() throws Exception {

        Scan scanUser3Car1 = new Scan("Els", "Audi", 2);

        // GET scan for Car 1 from User 3
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/els/car/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(null))
                );

        // POST scan for Car 1 from User 3
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(scanUser3Car1))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        mockMvc.perform(post("/statistics")
                .param("userName", scanUser3Car1.getUserName())
                .param("carBrand", scanUser3Car1.getCarBrand())
                .param("scoreNumber", scanUser3Car1.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("audi")))
                .andExpect(jsonPath("$.userScores[0].userName", is("els")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(2)));
    }

    // Vijfde test B: POST bestaande scan => redirect naar PUT
    @Test
    void whenPostStatitics_thenReturnUpdatedStatisticsJson() throws Exception {

        Scan scanUser1Car1bis = new Scan("Lode", "Audi", 7);

        // GET scan for Car 1 from User 3
        mockServer.expect(ExpectedCount.twice(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode/car/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(scanUser1Car1))
                );

        // PUT scan from User 1 for Car 1 with new score 1
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(scanUser1Car1bis))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        mockMvc.perform(post("/statistics")
                .param("userName", scanUser1Car1bis.getUserName())
                .param("carBrand", scanUser1Car1bis.getCarBrand())
                .param("scoreNumber", scanUser1Car1bis.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("audi")))
                .andExpect(jsonPath("$.userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(7)));
    }


    // Zesde test A: PUT bestaande scan
    @Test
    void whenUpdateStatistics_thenReturnUpdatedStatistiscsJson() throws Exception {

        Scan updatedScanUser1Car1 = new Scan("Lode", "Audi", 1);

        // GET scan from User 1 of Car 1
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode/car/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(scanUser1Car1))
                );

        // PUT scan from User 1 for Car 1 with new score 1
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedScanUser1Car1))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/audi")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car1))
                );

        mockMvc.perform(put("/statistics")
                .param("userName", updatedScanUser1Car1.getUserName())
                .param("carBrand", updatedScanUser1Car1.getCarBrand())
                .param("scoreNumber", updatedScanUser1Car1.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("audi")))
                .andExpect(jsonPath("$.userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(1)));

    }


    // Zesde test B: PUT van nog niet bestaande scan => redirect naar POST
    @Test
    void whenUpdateStatisticsOfNonExisting_thenReturnPostedStatistiscsJson() throws Exception {

        Scan newScanUser1Car5 = new Scan("Lode", "Ferarri", 1);

        // GET scan from User 1 of Car 1
        mockServer.expect(ExpectedCount.twice(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode/car/ferarri")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(null))
                );

        // POST scan for Car 1 from User 3
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(newScanUser1Car5))
                );

        // GET Car 1 info
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + carServiceBaseUrl + "/cars/ferarri")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car3))
                );

        mockMvc.perform(put("/statistics")
                .param("userName", newScanUser1Car5.getUserName())
                .param("carBrand", newScanUser1Car5.getCarBrand())
                .param("scoreNumber", newScanUser1Car5.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand", is("ferarri")))
                .andExpect(jsonPath("$.userScores[0].userName", is("lode")))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(1)));
    }

    // Zevende test
    @Test
    void whenDeleteRanking_thenReturnStatusOk() throws Exception {

        // DELETE scan from User 999 of Car with ISBN9 as ISBN
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + scanServiceBaseUrl + "/scans/user/lode/car/volvo")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/statistics/{userName}/car/{carBrand}", "lode", "volvo"))
                .andExpect(status().isOk());
    }

}