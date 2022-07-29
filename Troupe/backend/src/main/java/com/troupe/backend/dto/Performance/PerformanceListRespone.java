package com.troupe.backend.dto.Performance;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceListRespone {
    int pfNo;
    String description;
    String posterUrl;
    String location;
    String detailTime;
}
