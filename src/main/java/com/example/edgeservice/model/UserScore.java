package com.example.edgeservice.model;

public class UserScore {
    private String userName;
    private Integer scoreNumber;

    public UserScore(String userName, Integer scoreNumber) {
        this.userName = userName.toLowerCase();
        this.scoreNumber = scoreNumber;
    }

    public String getUserName() {
        return userName;
    }


    public Integer getScoreNumber() {
        return scoreNumber;
    }

}
