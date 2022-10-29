package com.example.edgeservice;

import com.example.edgeservice.controller.UserStatisticsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EdgeServiceApplicationTests {

    @Autowired
    private UserStatisticsController userStatisticsController;

    @Test
    void contextLoads() throws Exception {
        assertThat(userStatisticsController).isNotNull();
    }

}
