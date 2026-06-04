package com.caddyvg.dto;

public class YardageResponse {
    private int frontYards;
    private int middleYards;
    private int backYards;
    private final int playsLikeYards;

    public YardageResponse(int frontYards, int middleYards, int backYards, int playsLikeYards) {
        this.frontYards = frontYards;
        this.middleYards = middleYards;
        this.backYards = backYards;
        this.playsLikeYards = playsLikeYards;
    }

    public int getFrontYards() {
        return frontYards;
    }

    public int getMiddleYards() {
        return middleYards;
    }

    public int getBackYards() {
        return backYards;
    }

    public int getPlaysLikeYards() {
        return playsLikeYards;
    }
}