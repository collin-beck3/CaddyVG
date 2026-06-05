package com.caddyvg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caddyvg.model.Hazard;
import com.caddyvg.model.Hole;
import com.caddyvg.repository.HazardRepository;
import com.caddyvg.repository.HoleRepository;

@RestController
@RequestMapping("/api/holes")
public class HoleController {

    private final HoleRepository holeRepository;
    private final HazardRepository hazardRepository;

    public HoleController(HoleRepository holeRepository, HazardRepository hazardRepository) {
        this.holeRepository = holeRepository;
        this.hazardRepository = hazardRepository;
    }

    @GetMapping("/{holeId}")
    public Hole getHoleById(@PathVariable Long holeId) {
        return holeRepository.findById(holeId)
                .orElseThrow(() -> new RuntimeException("Hole not found"));
    }

    @GetMapping("/{holeId}/hazards")
    public List<Hazard> getHazardsForHole(@PathVariable Long holeId) {
        return hazardRepository.findByHoleHoleId(holeId);
    }
}