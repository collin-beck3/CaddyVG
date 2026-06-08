package com.caddyvg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caddyvg.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByExternalCourseId(String externalCourseId);
}