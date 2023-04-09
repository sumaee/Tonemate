package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.PitchAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PitchAnalysisRepository extends JpaRepository<PitchAnalysis, Long> {
    Optional<PitchAnalysis> findByPitchId(Long id);

    List<PitchAnalysis> findAllByUserId(Long userId);

    Optional<PitchAnalysis> findByPitchIdAndUserId(Long pitchId, Long userId);

    List<PitchAnalysis> findAllByUserIdOrderByPitchIdDesc(Long userId);

    void deleteByPitchIdAndUserId(Long pitchId, Long userId);
}
