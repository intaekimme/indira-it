package com.troupe.backend.dto.performance;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestCategoryResponse {
    int categoryNo;
    String bigCategory;
    String smallCategory;
    String codeName;
    int count;
}
