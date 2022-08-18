package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformancePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformancePriceRepository extends JpaRepository <PerformancePrice, Integer> {
    /**
     * 공연 번호에 해당하는 가격들 찾기
     * @param performance
     * @return
     */
    List<PerformancePrice> findByPf(Performance performance);

    /**
     * 공연번호와 좌석 id로 좌석 정보 찾기
     * @param performance
     * @param id
     * @return
     */
    PerformancePrice findByPfAndId(Performance performance, Integer id);

    /**
     * 공연번호와 좌석이름으로 좌석 정보 찾기
     * @param performance
     * @param seat
     * @return
     */
    PerformancePrice findByPfAndSeat(Performance performance, String seat);


}
