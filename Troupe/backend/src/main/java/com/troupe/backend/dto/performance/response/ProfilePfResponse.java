package com.troupe.backend.dto.performance.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePfResponse {
    int pfNo;       //  공연 번호
    int memberNo;   //  유저 번호
    String nickname;    //  유저 닉네임
    String title;   //  공연 제목
    String description; //  공연 상세 설명
    Map<Integer, String> images;    //  이미지 url
    String poster;  //  포스터 url
    String location; //  공연 장소
    String category;    //  카테고리
    String status;  //  공연 상태
    int runtime; //  상영 시간
    Date startDate; //  공연 시작일
    Date endDate;   // 공연 종료일
}
