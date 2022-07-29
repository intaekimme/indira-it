package com.troupe.backend.dto.Performance;


import com.troupe.backend.domain.performance.Performance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceResponse {
    int pfNo;
    String description;
    List<String> image;
    String location;
    String detailTime;

    public static PerformanceResponse PerformanceToResponse(Performance p, List<String> imgUrlList){
        return PerformanceResponse.builder()
                .pfNo(p.getId())
                .description(p.getDescription())
                .image(imgUrlList)
                .location(p.getLocation())
                .detailTime(p.getDetailTime())
                .build();
    }

}
