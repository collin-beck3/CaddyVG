package com.caddyvg.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Hole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holeId;

    private int holeNumber;
    private int par;
    private int handicap;

    private Double teeLatitude;
    private Double teeLongitude;

    private Double frontGreenLatitude;
    private Double frontGreenLongitude;

    private Double middleGreenLatitude;
    private Double middleGreenLongitude;

    private Double backGreenLatitude;
    private Double backGreenLongitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "hole", cascade = CascadeType.ALL)
    private List<Hazard> hazards = new ArrayList<>();

    public Hole() {
    }

    public Long getHoleId() {
        return holeId;
    }

    public void setHoleId(Long holeId) {
        this.holeId = holeId;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getHandicap() {
        return handicap;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }

    public Double getTeeLatitude() {
        return teeLatitude;
    }

    public void setTeeLatitude(Double teeLatitude) {
        this.teeLatitude = teeLatitude;
    }

    public Double getTeeLongitude() {
        return teeLongitude;
    }

    public void setTeeLongitude(Double teeLongitude) {
        this.teeLongitude = teeLongitude;
    }

    public Double getFrontGreenLatitude() {
        return frontGreenLatitude;
    }

    public void setFrontGreenLatitude(Double frontGreenLatitude) {
        this.frontGreenLatitude = frontGreenLatitude;
    }

    public Double getFrontGreenLongitude() {
        return frontGreenLongitude;
    }

    public void setFrontGreenLongitude(Double frontGreenLongitude) {
        this.frontGreenLongitude = frontGreenLongitude;
    }

    public Double getMiddleGreenLatitude() {
        return middleGreenLatitude;
    }

    public void setMiddleGreenLatitude(Double middleGreenLatitude) {
        this.middleGreenLatitude = middleGreenLatitude;
    }

    public Double getMiddleGreenLongitude() {
        return middleGreenLongitude;
    }

    public void setMiddleGreenLongitude(Double middleGreenLongitude) {
        this.middleGreenLongitude = middleGreenLongitude;
    }

    public Double getBackGreenLatitude() {
        return backGreenLatitude;
    }

    public void setBackGreenLatitude(Double backGreenLatitude) {
        this.backGreenLatitude = backGreenLatitude;
    }

    public Double getBackGreenLongitude() {
        return backGreenLongitude;
    }

    public void setBackGreenLongitude(Double backGreenLongitude) {
        this.backGreenLongitude = backGreenLongitude;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Hazard> getHazards() {
        return hazards;
    }

    public void setHazards(List<Hazard> hazards) {
        this.hazards = hazards;
    }
}