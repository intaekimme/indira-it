package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.PerformancePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformancePriceRepository extends JpaRepository <PerformancePrice, Integer> {

}
