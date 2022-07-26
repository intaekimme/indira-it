package com.troupe.backend.domain.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@Table(name = "tb_performance")
@NoArgsConstructor
@AllArgsConstructor
public class Performance implements Serializable {

    @Id
    @Column(name = "pf_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String location;

    private int runtime;

    @Size(max = 5000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Size(max = 1000)
    private String posterUrl;

    private int codeNo;

    private String detailTime;

    private boolean isRemoved;
    
}
