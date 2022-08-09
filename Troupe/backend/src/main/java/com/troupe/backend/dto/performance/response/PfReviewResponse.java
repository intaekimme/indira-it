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
    int memberNo;
    int reviewNo;
    String nickname;
    String profileImageUrl;
    String comment;
}