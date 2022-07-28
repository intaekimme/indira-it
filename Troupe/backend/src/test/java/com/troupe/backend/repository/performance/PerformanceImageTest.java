package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformanceImageTest {
    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceImageRepository performanceImageRepository;

    @Test
    @DisplayName("이미지 삭제")
    void 이미지삭제(){
        PerformanceImage targetPerformanceImage = performanceImageRepository.findById(5).get();
        performanceImageRepository.delete(targetPerformanceImage);

    }
}
