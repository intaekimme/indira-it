package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {

}
