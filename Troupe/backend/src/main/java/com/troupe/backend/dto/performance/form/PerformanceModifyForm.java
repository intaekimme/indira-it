package com.troupe.backend.dto.performance.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceModifyForm {

    private String title;
    private String location;
    private String runtime;
    private String description;
    private String category;
    private String detailTime;
    private String seatPrice;
    private Date startDate;
    private Date endDate;
    private List<MultipartFile> images;
    private List<Integer> imageNo;


}
