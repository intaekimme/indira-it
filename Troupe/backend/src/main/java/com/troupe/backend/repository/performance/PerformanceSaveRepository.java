package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.PerformanceSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSaveRepository extends JpaRepository<PerformanceSave, Integer> {

}
