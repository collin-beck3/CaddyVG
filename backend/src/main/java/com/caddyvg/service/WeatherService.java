package com.caddyvg.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.caddyvg.dto.WeatherResponse;

@Service
public class WeatherService {

    private final RestClient restClient = RestClient.create("https://api.open-meteo.com");

    public WeatherResponse getWeather(Double latitude, Double longitude) {

        Map response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current", "temperature_2m,wind_speed_10m,wind_direction_10m")
                        .queryParam("temperature_unit", "fahrenheit")
                        .queryParam("wind_speed_unit", "mph")
                        .build())
                .retrieve()
                .body(Map.class);

        Map<String, Object> current = (Map<String, Object>) response.get("current");

        Double temperature = ((Number) current.get("temperature_2m")).doubleValue();
        Double windSpeed = ((Number) current.get("wind_speed_10m")).doubleValue();
        Double windDirection = ((Number) current.get("wind_direction_10m")).doubleValue();

        return new WeatherResponse(temperature, windSpeed, windDirection);
    }
}