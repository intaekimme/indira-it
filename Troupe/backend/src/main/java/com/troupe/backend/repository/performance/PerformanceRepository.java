package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {
    /**
     * 제목에 매개변수를 포함한 공연 검색 %like%
     * @param title
     * @return List<Performance>
     */
    List<Performance> findByTitleContaining(String title);

    /**
     * 제목과 일치한 공연 검색
     * @param title
     * @return Performance
     */
    Performance findByTitleLike(String title);
}
