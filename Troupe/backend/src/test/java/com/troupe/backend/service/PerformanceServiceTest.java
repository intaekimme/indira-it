package com.troupe.backend.service;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.service.Performance.PerformanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PerformanceServiceTest {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    PerformanceRepository performanceRepository;

    @Test
    @DisplayName("공연 삭제 테스트")
    void 공연삭제테스트(){
        int memberNo = 3;
        int pfNo = 20;

        performanceService.deletePerformance(3, 20);

        Performance performance = performanceRepository.findById(20).get();
        Assertions.assertTrue(performance.getRemoved());
    }
}
