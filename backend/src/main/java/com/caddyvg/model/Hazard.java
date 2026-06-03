package com.caddyvg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Hazard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hazardId;

    private String type;
    private String label;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "hole_id")
    private Hole hole;

    public Hazard() {
    }

    // getters and setters
}