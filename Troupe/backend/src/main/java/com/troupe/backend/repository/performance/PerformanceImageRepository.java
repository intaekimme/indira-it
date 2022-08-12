package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import com.troupe.backend.domain.performance.PerformanceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceImageRepository extends JpaRepository <PerformanceImage, Integer> {
    /**
     * 공연 번호에 해당하는 이미지 모두 찾기
     * @param performance
     * @return List<PerformanceImage>
     */
    List<PerformanceImage> findByPf(Performance performance);

    /**
     * 이미지 url로 공연이미지 정보 찾기
     * @param url
     * @return
     */
    PerformanceImage findByImageUrl(String url);
}
