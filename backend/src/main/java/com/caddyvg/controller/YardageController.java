package com.caddyvg.controller;

import com.caddyvg.dto.YardageRequest;
import com.caddyvg.dto.YardageResponse;
import com.caddyvg.model.Hole;
import com.caddyvg.repository.HoleRepository;
import com.caddyvg.service.YardageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yardage")
public class YardageController {

    private final HoleRepository holeRepository;
    private final YardageService yardageService;

    public YardageController(HoleRepository holeRepository, YardageService yardageService) {
        this.holeRepository = holeRepository;
        this.yardageService = yardageService;
    }

    @PostMapping
    public YardageResponse getYardage(@RequestBody YardageRequest request) {
        Hole hole = holeRepository.findById(request.getHoleId())
                .orElseThrow(() -> new RuntimeException("Hole not found"));

        int front = yardageService.calculateDistanceInYards(
                request.getUserLatitude(),
                request.getUserLongitude(),
                hole.getFrontGreenLatitude(),
                hole.getFrontGreenLongitude()
        );

        int middle = yardageService.calculateDistanceInYards(
                request.getUserLatitude(),
                request.getUserLongitude(),
                hole.getMiddleGreenLatitude(),
                hole.getMiddleGreenLongitude()
        );

        int back = yardageService.calculateDistanceInYards(
                request.getUserLatitude(),
                request.getUserLongitude(),
                hole.getBackGreenLatitude(),
                hole.getBackGreenLongitude()
        );

        return new YardageResponse(front, middle, back, middle);
    }
}