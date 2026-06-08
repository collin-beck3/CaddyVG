package com.caddyvg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caddyvg.dto.CourseSearchResult;
import com.caddyvg.service.OpenStreetMapService;

@RestController
@RequestMapping("/api/courses/search")
public class CourseSearchController {

    private final OpenStreetMapService openStreetMapService;

    public CourseSearchController(OpenStreetMapService openStreetMapService) {
        this.openStreetMapService = openStreetMapService;
    }

    @GetMapping
    public List<CourseSearchResult> searchCourses(@RequestParam String name) {
        return openStreetMapService.searchGolfCourses(name);
    }
}
