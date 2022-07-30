package com.troupe.backend.dto.Performance.converter;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.dto.Performance.PerformanceForm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class toPerformanceEntity {

    /**
     * 폼과 앞서 조회한 유저 정보를 가지고 공연 엔티티로 변환
     * @param performanceForm
     * @param member
     * @return
     */
    public Performance whenCreate(PerformanceForm performanceForm, Member member){
        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        return Performance.builder()
                .memberNo(member)
                .title(performanceForm.getTitle())
                .location(performanceForm.getLocation())
                .runtime(performanceForm.getRuntime())
                .createdTime(now)
                .posterUrl(performanceForm.getPosterUrl())
                .codeNo(performanceForm.getCodeNo())
                .detailTime(performanceForm.getDetailTime())
                .description(performanceForm.getDescription())
                .build();
    }

    /**
     * 폼과 앞서 조회한 공연 정보와 유저 정보를 가지고 공연 엔티티로 변환
     * @param performanceForm
     * @param member
     * @param performance
     * @return
     */
    public Performance whenUpdate(PerformanceForm performanceForm, Member member, Performance performance){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date now = java.sql.Date.valueOf(String.valueOf(localDateTime));
        return Performance.builder()
                .memberNo(member)
                .title(performanceForm.getTitle())
                .location(performanceForm.getLocation())
                .runtime(performanceForm.getRuntime())
                .createdTime(performance.getCreatedTime())
                .updatedTime(now)
                .posterUrl(performanceForm.getPosterUrl())
                .codeNo(performanceForm.getCodeNo())
                .detailTime(performanceForm.getDetailTime())
                .description(performanceForm.getDescription())
                .build();
    }
}
