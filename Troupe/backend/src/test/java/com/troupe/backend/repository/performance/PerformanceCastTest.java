package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceCast;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PerformanceCastTest {
    @Autowired
    PerformanceRepository pr;

    @Autowired
    PerformanceCastRepository pcr;

    @Test
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
}
