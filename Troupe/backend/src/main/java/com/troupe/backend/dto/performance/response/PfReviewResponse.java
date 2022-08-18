package com.troupe.backend.dto.performance.response;

import com.troupe.backend.domain.performance.PerformanceReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PfReviewResponse {
    int reviewNo;
    int memberNo;
    int pfNo;
    int parentCommentNo;
    //List<PerformanceReview> childComments;        fetch.lazy로 인해 데이터를 불러 올 수 없고 fetch.eager로 변경 시 stackOverflow 에러 발생
    List<PfReviewResponse> childComments;
    String nickname;
    String profileImageUrl;
    Date createdTime;
    String comment;
    boolean isRemoved;
    boolean isModified;
}