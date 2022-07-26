package com.troupe.backend.domain.performance;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Table(name = "tb_pf_image")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceImage implements Serializable {

    @Id
    @Column(name = "image_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @Size(max = 1000)
    private String imageUrl;
}
