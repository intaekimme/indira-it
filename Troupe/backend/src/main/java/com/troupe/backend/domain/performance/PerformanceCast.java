package com.troupe.backend.domain.performance;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Getter
@Table(name = "tb_pf_cast")
@Entity
public class PerformanceCast {

    @Id
    @Column(name = "pf_cast_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int castNo;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pfNo")
    private Performance pf;

    @Size(max = 100)
    private String name;

}
