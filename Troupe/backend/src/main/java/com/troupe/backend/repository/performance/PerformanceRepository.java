package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {
//    List<Performance> findByTitle
}
