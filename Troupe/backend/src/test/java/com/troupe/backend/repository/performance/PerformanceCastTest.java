package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PerformanceCastTest {
    @Autowired
    PerformanceRepository pr;

    @Autowired
    PerformanceCastRepository pcr;

    @Test
    @DisplayName("캐스팅 생성")
    public void saveTest() {
        Performance performance = pr.getById(1);

        PerformanceCast performanceCast = PerformanceCast.builder()
                .pf(performance)
                .castNo(3)
                .name("동그라미")
                .build();

        PerformanceCast savePerformanceCast = pcr.save(performanceCast);

//        Assertions.assertEquals(1, savePerformanceCast.getCastNo());
//        Assertions.assertEquals("홍길동", savePerformanceCast.getName());

    }

    /** Read */
    @Test
    @DisplayName("공연번호로 캐스팅 찾기")
    void findByPf(){
        Performance performance = pr.findById(1).get();
        List<PerformanceCast> performanceCastList = pcr.findByPf(performance);

        Assertions.assertEquals("우영우",  performanceCastList.get(1).getName());
    }


    @Test
    @DisplayName("공연번호에 해당하는 캐스팅 수정")
    void 공연번호에_해당하는_캐스팅_수정() {
        Performance performance = pr.findById(1).get();
        List<PerformanceCast> performanCastList = pcr.findByPf(performance);

        PerformanceCast performanceCast = performanCastList.get(4);
        performanceCast.setCastNo(6);
        performanceCast.setName("주호민(광두)");

        PerformanceCast savePerformanceCast = pcr.save(performanceCast);
        Assertions.assertEquals("주호민(광두)", savePerformanceCast.getName());
    }

    @Test
    @DisplayName("공연번호와 배우 이름으로 삭제")
    void 공연번호와_배우_이름으로_삭제(){
        Performance performance = pr.findById(1).get();

        PerformanceCast performanceCast = pcr.findByPfAndName(performance, "주호민(광두)");
        pcr.delete(performanceCast);

        Assertions.assertEquals(Optional.empty(), pcr.findById(5));
    }




}
