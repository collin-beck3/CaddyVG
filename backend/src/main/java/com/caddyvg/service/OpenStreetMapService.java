package com.caddyvg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.caddyvg.dto.CourseSearchResult;

@Service
public class OpenStreetMapService {

    private final RestClient restClient = RestClient.create("https://nominatim.openstreetmap.org");

    public List<CourseSearchResult> searchGolfCourses(String name) {

        List<Map<String, Object>> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", name + " golf course")
                        .queryParam("format", "json")
                        .queryParam("limit", "10")
                        .queryParam("addressdetails", "1")
                        .build())
                .header("User-Agent", "CaddyVG/0.1 (collin.beck@bestegg.com)")
                .retrieve()
                .body(List.class);

        List<CourseSearchResult> results = new ArrayList<>();

        if (response == null) {
            return results;
        }

        for (Map<String, Object> item : response) {
            String displayName = String.valueOf(item.get("display_name"));

            String osmType = String.valueOf(item.get("osm_type"));
            String osmId = String.valueOf(item.get("osm_id"));
            String externalCourseId = osmType + "/" + osmId;

            Double latitude = Double.valueOf(String.valueOf(item.get("lat")));
            Double longitude = Double.valueOf(String.valueOf(item.get("lon")));

            results.add(new CourseSearchResult(
                    externalCourseId,
                    displayName,
                    latitude,
                    longitude
            ));
        }

        return results;
    }

    public Map getGolfFeaturesNearCourse(Double latitude, Double longitude) {

    String query = """
            [out:json][timeout:60];
            (
              way(around:1200,%f,%f)["golf"~"^(hole|tee|green|bunker)$"];
            );
            out center tags;
            """.formatted(
            latitude, longitude
    );

    return RestClient.create("https://overpass-api.de/api")
            .post()
            .uri("/interpreter")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .body("data=" + java.net.URLEncoder.encode(
                    query,
                    java.nio.charset.StandardCharsets.UTF_8
            ))
            .retrieve()
            .body(Map.class);
}
}
