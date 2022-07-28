package com.troupe.backend.repository.performance;


import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import com.troupe.backend.domain.performance.PerformancePrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformancePriceTest {
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    PerformancePriceRepository performancePriceRepository;

    @Test
    @DisplayName("가격 생성")
    public void saveTest() {
        Performance performance = performanceRepository.findById(1).get();

        PerformancePrice performancePrice = PerformancePrice.builder()
                .pf(performance)
                .seat("일반석")
                .price(10000)
                .build();

        PerformancePrice savePerformancePrice = performancePriceRepository.save(performancePrice);

        Assertions.assertEquals(10000, savePerformancePrice.getPrice());

    }


}
