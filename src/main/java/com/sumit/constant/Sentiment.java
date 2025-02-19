package com.sumit.constant;

public enum Sentiment {

    ANXIOUS("If user did not post any journal"),
    SAD("If user posted 1-2 journal per week"),
    HAPPY ("If user posted more than 2 journal per week");

    public final String description;

    Sentiment(String description){
        this.description = description;
    }


}