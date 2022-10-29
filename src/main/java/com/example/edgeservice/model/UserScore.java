package com.example.edgeservice.model;

public class UserScore {
    private String userName;
    private Integer scoreNumber;

    public UserScore(String userName, Integer scoreNumber) {
        this.userName = userName;
        this.scoreNumber = scoreNumber;
    }

    public String getUserName() {
        return userName;
    }

//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public Integer getScoreNumber() {
        return scoreNumber;
    }

//    public void setScoreNumber(Integer scoreNumber) {
//        this.scoreNumber = scoreNumber;
//    }
}
