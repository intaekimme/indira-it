package com.troupe.backend.dto.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
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
public class Performanceform {

    private String title;
    private String location;
    private int runtime;
    private String description;
    private String posterUrl;
    private int codeNo;
    private String detailTime;

    private String seat;
    private int price;

    public Performance createEntity(Member member){
        return Performance.builder()
                .memberNo(member)
                .title(this.title)
                .location(this.location)
                .runtime(this.runtime)
                .createdTime(new Date())
                .posterUrl(this.posterUrl)
                .codeNo(this.codeNo)
                .detailTime(this.detailTime)
                .description(this.description)
                .build();
    }

    public Performance updateEntity(Member member, Performance performance){
        return Performance.builder()
                .memberNo(member)
                .title(this.title)
                .location(this.location)
                .runtime(this.runtime)
                .createdTime(performance.getCreatedTime())
                .updatedTime(new Date())
                .posterUrl(this.posterUrl)
                .codeNo(this.codeNo)
                .detailTime(this.detailTime)
                .description(this.description)
                .build();
    }

}
