package com.caddyvg.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caddyvg.dto.CourseImportRequest;
import com.caddyvg.model.Course;
import com.caddyvg.service.CourseImportService;
import com.caddyvg.service.OpenStreetMapService;

@RestController
@RequestMapping("/api/courses/import")
public class CourseImportController {

    private final CourseImportService courseImportService;
    private final OpenStreetMapService openStreetMapService;

    public CourseImportController(
            CourseImportService courseImportService,
            OpenStreetMapService openStreetMapService
    ) {
        this.courseImportService = courseImportService;
        this.openStreetMapService = openStreetMapService;
    }

    @PostMapping
    public Course importCourse(@RequestBody CourseImportRequest request) {
        return courseImportService.importCourse(request);
    }

    @GetMapping("/features")
    public Map testFeatures(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        return openStreetMapService.getGolfFeaturesNearCourse(
                latitude,
                longitude
        );
    }
}