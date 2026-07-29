package com.caddyvg.dto;

public class WeatherResponse {

    private Double temperature;
    private Double windSpeed;
    private Double windDirection;

    public WeatherResponse(Double temperature, Double windSpeed, Double windDirection) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindDirection() {
        return windDirection;
    }
}