package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceCastRepository extends JpaRepository <PerformanceCast, Integer> {
    List<PerformanceCast> findByPf(Performance performance);
    PerformanceCast findByPfAndName(Performance performance, String name);

    PerformanceCast findByName(String name);


}
