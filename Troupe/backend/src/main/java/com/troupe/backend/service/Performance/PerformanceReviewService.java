package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
import com.troupe.backend.dto.Performance.PfReviewResponse;
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
import java.util.*;

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

    /**
     * 공연 후기 삭제
     * @param pfNo
     * @param reviewNo
     */
    @Transactional
    public void delete(int pfNo, int reviewNo) {
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연입니다."));
        //  refactoring : 리뷰 번호로만 삭제해도 되지않을까?
        PerformanceReview performanceReview = performanceReviewRepository.findBypfNoAndId(performance, reviewNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));
        performanceReview.setRemoved(true);
        performanceReviewRepository.save(performanceReview);
    }

    /**
     * 공연 후기 리스트 반환
     * @param pfNo
     * @param memberNo
     * @return
     */
    @Transactional(readOnly = true)
    public List<PfReviewResponse> findPfReviewList(int pfNo, int memberNo){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연입니다."));
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findByPfNo(performance);
        List<PfReviewResponse> pfReviewResponseList = new ArrayList<>();
        for (PerformanceReview performanceReview : performanceReviewList){
            pfReviewResponseList.add( PfReviewResponse.builder()
                    .memberNo(memberNo)
                    .nickname(member.getNickname())
                    .profileImageUrl(member.getProfileImageUrl())
                    .comment(performanceReview.getContent())
                    .build()
            );
        }
        return pfReviewResponseList;
    }


}