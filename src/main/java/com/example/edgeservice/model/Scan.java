package com.example.edgeservice.model;

public class Scan {

    private String userName;
    private String carBrand;
    private Integer scoreNumber;

    public Scan() {
    }

    public Scan(String userName, String carBrand, Integer scoreNumber) {
        this.userName = userName.toLowerCase();
        this.carBrand = carBrand.toLowerCase();
        this.scoreNumber = scoreNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.toLowerCase();
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand.toLowerCase();
    }

    public Integer getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(Integer scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

}

