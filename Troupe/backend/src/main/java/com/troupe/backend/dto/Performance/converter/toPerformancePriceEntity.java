package com.troupe.backend.dto.Performance.converter;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformancePrice;
import com.troupe.backend.dto.Performance.PerformanceForm;
import com.troupe.backend.dto.Performance.Seat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class toPerformancePriceEntity {
    public List<PerformancePrice> whenCreateOrUpdate(PerformanceForm performanceForm, Performance performance){
        List<Seat> seatList = performanceForm.getPrice();
        List<PerformancePrice> entities = new ArrayList<PerformancePrice>();
        for(Seat seat : seatList){
            PerformancePrice p = PerformancePrice.builder()
                    .pf(performance)
                    .seat(seat.getName())
                    .price(seat.getPrice())
                    .build();
            entities.add(p);
        }
        return entities;
    }

}
