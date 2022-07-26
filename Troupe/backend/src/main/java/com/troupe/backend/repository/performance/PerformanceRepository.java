package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {

}
