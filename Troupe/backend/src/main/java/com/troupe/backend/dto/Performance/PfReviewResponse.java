package com.troupe.backend.dto.Performance;

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
    String nickname;
    String profileImageUrl;
    String comment;
}
