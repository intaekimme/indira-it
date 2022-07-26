package com.troupe.backend.domain.performance;


import com.troupe.backend.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "tb_pf_save")
@Entity
public class PerformanceSave {

    @Id
    @Column(name = "pf_save_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "pf_no")
    private Performance pfNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

}
