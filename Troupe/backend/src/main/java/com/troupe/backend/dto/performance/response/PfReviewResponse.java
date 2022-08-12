package com.troupe.backend.dto.performance.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PfReviewResponse {
    int reviewNo;
    int pfNo;
    int memberNo;
    String nickname;
    String profileImageUrl;
    String comment;
}