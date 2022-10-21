package com.example.edgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class UserStatistics {

    private String carBrand;
    private List<UserScore> userScores;

    public UserStatistics(Car car, List<Scan> scans) {
        setCarBrand(car.getCarBrand());
//        setISBN(book.getISBN());
        userScores = new ArrayList<>();
        scans.forEach(scan -> {
            userScores.add(new UserScore(scan.getUserName(),
                    scan.getScoreNumber()));
        });
        setUserScores(userScores);
    }

    public UserStatistics(Car car, Scan scan) {
        setCarBrand(car.getCarBrand());
//        setISBN(book.getISBN());
        userScores = new ArrayList<>();
        userScores.add(new UserScore(scan.getUserName(), scan.getScoreNumber()));
        setUserScores(userScores);
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        carBrand = carBrand;
    }

    public List<UserScore> getUserScores() {
        return userScores;
    }

    public void setUserScores(List<UserScore> userScores) {
        this.userScores = userScores;
    }
}
