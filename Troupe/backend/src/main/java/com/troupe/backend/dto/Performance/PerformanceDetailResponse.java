package com.troupe.backend.dto.Performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceDetailResponse {
    int pfNo;
    Map<Integer, String> imageUrl;
    int memberNo;
    String title;
    String location;
    int runtime;
    String description;
    Date createdTime;
    Date updatedTime;
    int categoryNo;
    String detailTime;
    boolean isRemoved;

}