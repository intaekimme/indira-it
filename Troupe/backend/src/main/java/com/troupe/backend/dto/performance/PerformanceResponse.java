package com.troupe.backend.dto.performance;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceResponse {
    int pfNo;
    String description;
    Map<Integer, String> image;
    String location;
    String detailTime;
}
