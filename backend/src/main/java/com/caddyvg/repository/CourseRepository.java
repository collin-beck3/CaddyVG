package com.caddyvg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caddyvg.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}