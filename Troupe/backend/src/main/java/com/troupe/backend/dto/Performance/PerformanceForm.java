package com.troupe.backend.dto.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformancePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String posterUrl;       //  String에서 List<MultipartFile>
    private int codeNo;
    private String detailTime;

   private List<Seat> price;

    public Performance createPerformanceEntity(Member member){
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

    public Performance updatePerformanceEntity(Member member, Performance performance){
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

    public void createPerformancePriceEntities(Performance performance){
        List<Seat> seatList = this.price;
        List<PerformancePrice> entities = new ArrayList<PerformancePrice>();
        for(Seat seat : seatList){
            PerformancePrice p = PerformancePrice.builder()
                    .pf(performance)
                    .seat(seat.getName())
                    .price(seat.getPrice())
                    .build();
            entities.add(p);
        }
    }

    public void updatePerformancePriceEntities(Performance performance){

    }

}
