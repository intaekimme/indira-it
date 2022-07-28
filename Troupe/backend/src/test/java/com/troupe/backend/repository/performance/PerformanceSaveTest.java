package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceSave;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformanceSaveTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceSaveRepository performanceSaveRepository;

    @Test
    @DisplayName("공연 저장 테스트")
    public void saveTest() throws ParseException {
        Member member = memberRepository.getById(3);
        Performance performance = performanceRepository.getById(13);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        PerformanceSave performanceSave = PerformanceSave.builder()
                .memberNo(member)
                .pfNo(performance)
                .createdTime(dateFormat.parse("20220802"))
                .build();

        PerformanceSave savePerformanceSave = performanceSaveRepository.save(performanceSave);

    }

    @Test
    @DisplayName("공연 저장 삭제 테스트")
    public void deleteTest(){
        Member member = memberRepository.findById(3).get();
        Performance performance = performanceRepository.findById(2).get();

        //  삭제할 공연
        PerformanceSave targetPerformanceSave = performanceSaveRepository.findByMemberNoAndPfNo(member, performance);
        targetPerformanceSave.setRemoved(true);

        performanceSaveRepository.save(targetPerformanceSave);

        Assertions.assertTrue(targetPerformanceSave.getRemoved());

    }

}