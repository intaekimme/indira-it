package com.troupe.backend.dto.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PfReviewForm {
    private int pfNo;
    private int memberNo;
    private int parentReviewNo;
    private String content;
}