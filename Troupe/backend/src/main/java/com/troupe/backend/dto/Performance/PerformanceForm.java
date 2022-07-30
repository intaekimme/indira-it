package com.troupe.backend.dto.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformancePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private int codeNo;
    private String detailTime;
    private List<Seat> price;


}
