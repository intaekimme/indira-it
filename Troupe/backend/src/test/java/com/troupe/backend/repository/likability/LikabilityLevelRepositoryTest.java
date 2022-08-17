package com.troupe.backend.repository.likability;

import com.troupe.backend.domain.likability.LikabilityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikabilityLevelRepositoryTest {
    @Autowired
    LikabilityLevelRepository llr;

//    @Test
//    @DisplayName("호감도 레벨 등록, 조회 테스트")
//    public void saveAndFindTest () {
//        LikabilityLevel level1 = llr.save(LikabilityLevel.builder().level(1).requiredExp(0).build());
//        LikabilityLevel level2 = llr.save(LikabilityLevel.builder().level(2).requiredExp(10).build());
//
//        assertThat(llr.findById(1).get()).isEqualTo(level1);
//        assertThat(llr.findById(2).get()).isEqualTo(level2);
////        assertThat(llr.findById(3).isPresent()).isEqualTo(false);
//    }
//
//    @Test
//    @DisplayName("호감도 레벨 등록, 수정 테스트")
//    public void saveAndUpdateTest() {
//        LikabilityLevel level = llr.save(LikabilityLevel.builder().level(1).requiredExp(0).build());
//        assertThat(llr.findById(1).get().getRequiredExp()).isEqualTo(0);
//
//        level.setRequiredExp(500);
//        assertThat(llr.findById(1).get().getRequiredExp()).isEqualTo(500);
//    }
//
//    @Test
//    @DisplayName("호감도 레벨 등록, 삭제 테스트")
//    public void saveAndDeleteTest() {
//        LikabilityLevel level = llr.save(LikabilityLevel.builder().level(1).requiredExp(0).build());
//        assertThat(llr.findById(1).isPresent()).isEqualTo(true);
//
//        llr.delete(level);
//        assertThat(llr.findById(1).isPresent()).isEqualTo(false);
//    }

}
