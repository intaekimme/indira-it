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
@Table(name = "tb_pf_cast")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceCast implements Serializable {

    @Id
    @Column(name = "pf_cast_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer castNo;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pfNo")
    private Performance pf;

    @Size(max = 100)
    private String name;

}
