package com.caddyvg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caddyvg.model.Hazard;

public interface HazardRepository extends JpaRepository<Hazard, Long> {
    List<Hazard> findByHoleHoleId(Long holeId);
}