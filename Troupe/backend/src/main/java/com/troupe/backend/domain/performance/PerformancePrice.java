package com.troupe.backend.domain.performance;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Table(name = "tb_pf_price")
@Entity
public class PerformancePrice {

    @Id
    @Column(name = "pf_price_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @Size(max = 10)
    private String seat;

    private int price;
}
