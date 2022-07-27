package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
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
public class PerformanceReviewTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceReviewRepository performanceReviewRepository;

    @Test
    public void saveTest() throws ParseException  {
        Member member = memberRepository.getById(3);
        Performance performance = performanceRepository.getById(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        PerformanceReview performanceReview = PerformanceReview.builder()
                .memberNo(member)
                .pfNo(performance)
                .createdTime(dateFormat.parse("20220802"))
                .isModified(false)
                .isRemoved(false)
                .content("댓글 1")
                .build();

        PerformanceReview savePerformanceReview = performanceReviewRepository.save(performanceReview);

        Assertions.assertEquals("댓글 1", savePerformanceReview.getContent());

    }

}
