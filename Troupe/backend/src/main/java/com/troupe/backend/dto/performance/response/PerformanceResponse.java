package com.troupe.backend.dto.performance.response;


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
    String title;
    String description;
    Map<Integer, String> image;
    String location;
    String detailTime;
    String category;
    String status;
}
