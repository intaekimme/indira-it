package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.performance.PerformancePrice;
import com.troupe.backend.repository.performance.PerformancePriceRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformancePriceService {
    private final PerformanceRepository performanceRepository;
    private final PerformancePriceRepository performancePriceRepository;

    @Transactional
    public List<PerformancePrice> addPerformanceList(int performanceNo){

    }

}
