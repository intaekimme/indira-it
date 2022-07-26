package com.troupe.backend.domain.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@Table(name = "tb_pf_price")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformancePrice implements Serializable {

    @Id
    @Column(name = "pf_price_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @Size(max = 10)
    private String seat;

    private int price;
}
