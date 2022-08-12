package com.troupe.backend.dto.likability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikabilityResponse {
    int exp;
    int level;
    int requiredExpNow;
    int requiredExpNext;
    int rank;
}
