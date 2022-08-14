package com.troupe.backend.dto.performance.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PfReviewResponse {
    int reviewNo;
    int memberNo;
    int pfNo;
    int parentCommentNo;
    String nickname;
    String profileImageUrl;
    Date createdTime;
    String comment;
    boolean isRemoved;
    boolean isModified;
}