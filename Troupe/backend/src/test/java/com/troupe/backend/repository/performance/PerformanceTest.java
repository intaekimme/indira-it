package com.troupe.backend.repository.performance;


import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformanceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PerformanceRepository performanceRepository;

    @Test
    public void saveTest() throws ParseException {

        Member member = memberRepository.getById(3);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Performance performance = Performance.builder()
                .memberNo(member)
                .title("공연2")
                .location("인천광역시 부평구 삼산동")
                .runtime(150)
                .createdTime(dateFormat.parse("20220728"))
                .updatedTime(dateFormat.parse("20220801"))
                .posterUrl("http://www.def.ghi")
                .codeNo(2)
                .detailTime("17:00")
                .description("test test test test test test test test test test test test")
                .isRemoved(false)
                .build();

        Performance savePerformance = performanceRepository.save(performance);

        Assertions.assertEquals("인천광역시 부평구 부개동", savePerformance.getLocation());
        Assertions.assertEquals("공연1", savePerformance.getTitle());

    }
}
