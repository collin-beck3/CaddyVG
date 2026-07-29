package com.caddyvg.dto;

public class PlaysLikeResponse {

    private Integer actualYardage;
    private Integer windAdjustment;
    private Integer playsLikeYardage;

    public PlaysLikeResponse(Integer actualYardage, Integer windAdjustment, Integer playsLikeYardage) {
        this.actualYardage = actualYardage;
        this.windAdjustment = windAdjustment;
        this.playsLikeYardage = playsLikeYardage;
    }

    public Integer getActualYardage() {
        return actualYardage;
    }

    public Integer getWindAdjustment() {
        return windAdjustment;
    }

    public Integer getPlaysLikeYardage() {
        return playsLikeYardage;
    }
}