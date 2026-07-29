package com.caddyvg.service;

import org.springframework.stereotype.Service;

import com.caddyvg.dto.PlaysLikeResponse;
import com.caddyvg.dto.WeatherResponse;

@Service
public class PlaysLikeService {

    private final WeatherService weatherService;

    public PlaysLikeService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public PlaysLikeResponse calculate(
            Double userLatitude,
            Double userLongitude,
            Double targetLatitude,
            Double targetLongitude
    ) {
        int actualYardage = calculateDistanceYards(
                userLatitude,
                userLongitude,
                targetLatitude,
                targetLongitude
        );

        WeatherResponse weather = weatherService.getWeather(userLatitude, userLongitude);

        double bearingToTarget = calculateBearing(
                userLatitude,
                userLongitude,
                targetLatitude,
                targetLongitude
        );

        int windAdjustment = calculateWindAdjustment(
                bearingToTarget,
                weather.getWindDirection(),
                weather.getWindSpeed()
        );

        int playsLikeYardage = actualYardage + windAdjustment;

        return new PlaysLikeResponse(
                actualYardage,
                windAdjustment,
                playsLikeYardage
        );
    }

    private int calculateDistanceYards(
            Double lat1,
            Double lon1,
            Double lat2,
            Double lon2
    ) {
        double earthRadiusMeters = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double meters = earthRadiusMeters * c;

        return (int) Math.round(meters * 1.09361);
    }

    private double calculateBearing(
            Double lat1,
            Double lon1,
            Double lat2,
            Double lon2
    ) {
        double startLat = Math.toRadians(lat1);
        double endLat = Math.toRadians(lat2);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double y = Math.sin(deltaLon) * Math.cos(endLat);
        double x =
                Math.cos(startLat) * Math.sin(endLat)
                        - Math.sin(startLat) * Math.cos(endLat) * Math.cos(deltaLon);

        double bearing = Math.toDegrees(Math.atan2(y, x));

        return (bearing + 360) % 360;
    }

    private int calculateWindAdjustment(
        double bearingToTarget,
        double windDirection,
        double windSpeed
) {
    if (windSpeed < 5) {
        return 0;
    }

    // Flip bearing because current calculation is reading opposite of shot direction
    double shotDirection = (bearingToTarget + 180) % 360;

    // windDirection = where wind comes FROM
    double angleDifference = Math.abs(shotDirection - windDirection);

    if (angleDifference > 180) {
        angleDifference = 360 - angleDifference;
    }

    double windComponent = windSpeed * Math.cos(Math.toRadians(angleDifference));

    if (Math.abs(windComponent) < 3) {
        return 0;
    }

    if (windComponent > 0) {
        // Headwind
        return (int) Math.round(windComponent);
    } else {
        // Tailwind
        return (int) Math.round(windComponent * 0.5);
    }
}
}
