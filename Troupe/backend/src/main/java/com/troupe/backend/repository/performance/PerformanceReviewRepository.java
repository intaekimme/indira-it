package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {

    /**
     * 공연번호와 리뷰번호로 특정 공연의 리뷰 하나 찾기
     * @param performance
     * @param reviewNo
     * @return
     */
    Optional<PerformanceReview> findBypfNoAndId(Performance performance, int reviewNo);
    /**
     * 공연 번호에 해당하는 리뷰 모두 찾기
     * @param performance
     * @return
     */
    List<PerformanceReview> findByPfNo(Performance performance);
    List<PerformanceReview> findByPfNoAndParentPerformanceReviewNo(Performance ParentPerformance, PerformanceReview performanceReview);
}
