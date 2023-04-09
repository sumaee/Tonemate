package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.TimbreAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimbreAnalysisRepository extends JpaRepository<TimbreAnalysis, Long> {
    Optional<TimbreAnalysis> findByTimbreIdAndUserId(Long timbreId, Long userId);

    List<TimbreAnalysis> findAllByUserId(Long userId);

    List<TimbreAnalysis> findAllByUserIdOrderByTimbreIdDesc(Long userId);

    void deleteByTimbreIdAndUserId(Long timbreId, Long userId);
}
