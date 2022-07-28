package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {
    List<PerformanceReview> findByPfNo(Performance performance);
}
