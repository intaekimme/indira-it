package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.repository.performance.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PerformanceReviewService {

    private final MemberRepository memberRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceReviewRepository performanceReviewRepository;

    /**
     * 공연후기 작성
     * @param pfNo
     * @param memberNo
     * @param content
     */
    @Transactional
    public void add(int pfNo, int memberNo, String content){

        //  비로그인 유저만의 값 이용
        if(memberNo == 0){
            log.info("유효 하지 않은 값");
            throw new NoSuchElementException("로그인이 필요합니다.");
        }
        Member member = memberRepository.getById(memberNo);
        Performance performance = performanceRepository.getById(pfNo);

        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        PerformanceReview performanceReview = PerformanceReview.builder()
                .memberNo(member)
                .pfNo(performance)
                .createdTime(now)
                .isModified(false)
                .isRemoved(false)
                .content(content)
                .build();

        performanceReviewRepository.save(performanceReview);
    }


}
