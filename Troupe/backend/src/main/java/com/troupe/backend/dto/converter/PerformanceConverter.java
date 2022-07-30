package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import com.troupe.backend.domain.performance.PerformancePrice;
import com.troupe.backend.dto.Performance.PerformanceForm;
import com.troupe.backend.dto.Performance.Seat;
import com.troupe.backend.util.S3FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class PerformanceConverter {

    @Autowired
    S3FileUploadService s3FileUploadService;

    /**
     * Create
     * PerformanceForm -> PerformancEntity
     * 폼과 앞서 조회한 유저 정보를 가지고 공연 엔티티로 변환
     * @param performanceForm
     * @param member
     * @return
     */
    public Performance toPerformanceEntityWhenCreate(PerformanceForm performanceForm, Member member){
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
                .codeNo(performanceForm.getCodeNo())
                .detailTime(performanceForm.getDetailTime())
                .description(performanceForm.getDescription())
                .build();
    }

    /**
     * Update
     * PerformanceForm -> PerformanceEntity
     * 폼과 앞서 조회한 공연 정보와 유저 정보를 가지고 공연 엔티티로 변환
     * @param performanceForm
     * @param member
     * @param performance
     * @return
     */
    public Performance toPerformanceEntityWhenUpdate(PerformanceForm performanceForm, Member member, Performance performance){
        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        return Performance.builder()
                .memberNo(member)
                .title(performanceForm.getTitle())
                .location(performanceForm.getLocation())
                .runtime(performanceForm.getRuntime())
                .createdTime(performance.getCreatedTime())
                .updatedTime(now)
//                .posterUrl(performanceForm.getPosterUrl())
                .codeNo(performanceForm.getCodeNo())
                .detailTime(performanceForm.getDetailTime())
                .description(performanceForm.getDescription())
                .build();
    }


    /**
     * Create, Update
     * PerformanceForm -> PerformancePriceEntity
     * 폼과 앞서 조회한 공연 정보를 가지고 좌석 엔티티로 변환
     * @param performanceForm
     * @param performance
     * @return
     */
    public List<PerformancePrice> toPerformancePriceEntityWhenCreateOrUpdate(PerformanceForm performanceForm, Performance performance){
        List<Seat> seatList = performanceForm.getPrice();
        List<PerformancePrice> entities = new ArrayList<PerformancePrice>();
        for(Seat seat : seatList){
            PerformancePrice p = PerformancePrice.builder()
                    .pf(performance)
                    .seat(seat.getName())
                    .price(seat.getPrice())
                    .build();
            entities.add(p);
        }
        return entities;
    }

    public List<PerformanceImage> toPerformanceImageEntityWhenCreate(List<String> urlList, Performance performance){
        List<PerformanceImage> performanceImageList = new ArrayList<>();
        for (String url : urlList){
            PerformanceImage p = PerformanceImage.builder()
                    .imageUrl(url)
                    .pf(performance)
                    .build();

            performanceImageList.add(p);
        }
        return performanceImageList;
    }

}
