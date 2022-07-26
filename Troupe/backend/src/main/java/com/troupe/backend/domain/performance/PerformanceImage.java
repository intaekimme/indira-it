package com.troupe.backend.domain.performance;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Table(name = "tb_pf_image")
@Entity
public class PerformanceImage {

    @Id
    @Column(name = "image_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @Size(max = 1000)
    private String imageUrl;
}
