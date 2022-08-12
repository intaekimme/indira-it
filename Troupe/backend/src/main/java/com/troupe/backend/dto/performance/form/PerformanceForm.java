package com.troupe.backend.dto.performance.form;

import com.troupe.backend.dto.performance.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceForm {

    private String title;
    private String location;
    private String runtime;
    private String description;
    private String detailTime;
    private List<MultipartFile> images;
    private String seatPrice;
    private String startDate;
    private String endDate;
    private String category;



}
