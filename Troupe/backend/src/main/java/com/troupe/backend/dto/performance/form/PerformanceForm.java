package com.troupe.backend.dto.performance.form;

import com.troupe.backend.dto.performance.Seat;
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
public class PerformanceForm {

    private String title;
    private String location;
    private int runtime;
    private String description;
    private int categoryNo;
    private String detailTime;
    private List<Seat> price;
    private Date startDate;
    private Date endDate;
    private String category;
    private List<MultipartFile> images;



}
