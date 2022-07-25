package com.troupe.backend.domain.performance;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "tb_performance")
public class Performance {

    @Id
    @Column(name = "pf_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String location;

    private Integer runtime;

    @Size(max = 5000)
    private String description;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    @Size(max = 1000)
    private String posterUrl;

    private Integer codeNo;

    private String detailTime;

    private boolean isRemoved;
    
}
