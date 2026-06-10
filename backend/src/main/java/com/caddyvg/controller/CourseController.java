package com.caddyvg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caddyvg.model.Course;
import com.caddyvg.model.Hole;
import com.caddyvg.repository.CourseRepository;
import com.caddyvg.repository.HoleRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final HoleRepository holeRepository;

    public CourseController(CourseRepository courseRepository, HoleRepository holeRepository) {
        this.courseRepository = courseRepository;
        this.holeRepository = holeRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @GetMapping("/{courseId}/holes")
    public List<Hole> getHolesForCourse(@PathVariable Long courseId) {
        return holeRepository.findByCourseCourseId(courseId);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseRepository.deleteById(courseId);
    }

    
}