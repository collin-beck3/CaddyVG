package com.caddyvg.model;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "hole", cascade = CascadeType.ALL)
    private List<Hazard> hazards = new ArrayList<>();

    public Hole() {
    }

    // getters and setters
    
    public double getFrontGreenLatitude() {
        return frontGreenLatitude;
    }
    
    public double getFrontGreenLongitude() {
        return frontGreenLongitude;
    }
    
    public double getMiddleGreenLatitude() {
        return middleGreenLatitude;
    }
    
    public double getMiddleGreenLongitude() {
        return middleGreenLongitude;
    }
    
    public double getBackGreenLatitude() {
        return backGreenLatitude;
    }
    
    public double getBackGreenLongitude() {
        return backGreenLongitude;
    }

    public Long getHoleId() {
    return holeId;
}

public int getHoleNumber() {
    return holeNumber;
}

public int getPar() {
    return par;
}

public int getHandicap() {
    return handicap;
}

public Double getTeeLatitude() {
    return teeLatitude;
}

public Double getTeeLongitude() {
    return teeLongitude;
}

}