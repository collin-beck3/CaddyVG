package com.caddyvg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caddyvg.model.Hole;

public interface HoleRepository extends JpaRepository<Hole, Long> {
    List<Hole> findByCourseCourseId(Long courseId);
}