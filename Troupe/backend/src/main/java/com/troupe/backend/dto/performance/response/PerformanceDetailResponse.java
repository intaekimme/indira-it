package com.troupe.backend.dto.performance.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceDetailResponse {
    int pfNo;       //  공연 번호, 비 필수
    Map<Integer, String> imageUrl;      //  공연 사진, 필수
    List<PriceResponse> price;       //  공연 좌석 정보, 필수
    int memberNo;   //  유저 번호, 비 필수
    String profileImg;  //  프로필 이미지, 필수
    String nickname;    //  닉네임, 필수
    String title;   //  제목, 필수
    String location;    //  장소, 필수
    int runtime;    //  상영시간, 필수
    String description; //  설명, 필수
    Date startDate; // 공연 시작일, 필수
    Date endDate;   // 공연 종료일, 필수
    Date createdTime;   //  공연 등록일, 비 필수
    Date updatedTime;   //  공연 수정일, 비 필수
    String category;    //  카테고리(장르), 필수
    String status;  //  공연상태, 필수
    String detailTime;  // 공연 상세 일, 비 필수
    boolean isRemoved;  //  삭제 여부, 비 필수

}