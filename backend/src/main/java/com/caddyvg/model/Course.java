package com.caddyvg.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String externalCourseId;

    private String name;
    private String city;
    private String state;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Hole> holes = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String city, String state, Double latitude, Double longitude) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // getters and setters

    public Long getCourseId() {
    return courseId;
}

public String getName() {
    return name;
}

public String getCity() {
    return city;
}

public String getState() {
    return state;
}

public Double getLatitude() {
    return latitude;
}

public Double getLongitude() {
    return longitude;
}

public void setCourseId(Long courseId) {
    this.courseId = courseId;
}

public void setName(String name) {
    this.name = name;
}

public void setCity(String city) {
    this.city = city;
}

public void setState(String state) {
    this.state = state;
}

public void setLatitude(Double latitude) {
    this.latitude = latitude;
}

public void setLongitude(Double longitude) {
    this.longitude = longitude;
}

public String getExternalCourseId() {
    return externalCourseId;
}

public void setExternalCourseId(String externalCourseId) {
    this.externalCourseId = externalCourseId;
}

}