package com.caddyvg.dto;

public class CourseSearchResult {

    private String externalCourseId;
    private String name;
    private Double latitude;
    private Double longitude;

    public CourseSearchResult(String externalCourseId, String name, Double latitude, Double longitude) {
        this.externalCourseId = externalCourseId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getExternalCourseId() {
        return externalCourseId;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}