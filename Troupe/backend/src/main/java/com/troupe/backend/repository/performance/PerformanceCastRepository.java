package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceCastRepository extends JpaRepository <PerformanceCast, Integer> {
    /**
     * 공연 번호에 해당하는 캐스팅 모두 찾기
     * @param performance
     * @return List<PerformanceCast>
     */
    List<PerformanceCast> findByPf(Performance performance);

    /**
     * 공연번호와 캐스팅 배우 이름으로 캐스팅 정보 찾기
     * @param performance
     * @param name
     * @return PerformanceCast
     */
    PerformanceCast findByPfAndName(Performance performance, String name);

    /**
     * 캐스팅 배우 이름으로 캐스팅 정보 찾기
     * @param name
     * @return PerformanceCast
     */
    PerformanceCast findByName(String name);


}
