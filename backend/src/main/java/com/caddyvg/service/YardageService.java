package com.caddyvg.service;

import org.springframework.stereotype.Service;

@Service
public class YardageService {

    public int calculateDistanceInYards(
            double userLat,
            double userLon,
            double targetLat,
            double targetLon
    ) {
        double earthRadiusMeters = 6371000;

        double lat1 = Math.toRadians(userLat);
        double lat2 = Math.toRadians(targetLat);
        double deltaLat = Math.toRadians(targetLat - userLat);
        double deltaLon = Math.toRadians(targetLon - userLon);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double meters = earthRadiusMeters * c;
        double yards = meters * 1.09361;

        return (int) Math.round(yards);
    }
}