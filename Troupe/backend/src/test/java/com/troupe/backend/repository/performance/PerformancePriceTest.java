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

import java.util.List;


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

    @Test
    @DisplayName("공연 번호에 해당하는 가격찾기")
    void 공연_번호에_해당하는_가격찾기(){
        Performance performance = performanceRepository.findById(1).get();
        List<PerformancePrice> performancePriceList = performancePriceRepository.findByPf(performance);

        Assertions.assertEquals(3,performancePriceList.size());
    }

    @Test
    @DisplayName("가격 수정")
    void 가격수정() {
        Performance performance = performanceRepository.findById(1).get();
        List<PerformancePrice> performancePriceList = performancePriceRepository.findByPf(performance);

        PerformancePrice p = performancePriceList.get(2);
        p.setPrice(60000);

        performancePriceRepository.save(p);

        Assertions.assertEquals(60000, performancePriceRepository.findById(3).get().getPrice());
    }


    @Test
    @DisplayName("공연번호와 좌석 id로 좌석 정보 찾기")
    void 공연번호와_좌석_id로_좌석_정보_찾기(){
        Performance performance = performanceRepository.findById(1).get();
        PerformancePrice performancePrice = performancePriceRepository.findByPfAndId(performance, 2);

        Assertions.assertEquals("S", performancePrice.getSeat());
    }

    @Test
    @DisplayName("공연번호와 좌석이름으로 좌석 정보 찾기")
    void 공연번호와_좌석이름으로_좌석_정보_찾기(){
        Performance performance = performanceRepository.findById(1).get();

        PerformancePrice performancePrice = performancePriceRepository.findByPfAndSeat(performance, "S");

        Assertions.assertEquals("S", performancePrice.getSeat());
    }
}
