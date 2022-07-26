package com.troupe.backend.domain.performance;


import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Table(name = "tb_pf_save")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSave implements Serializable {

    @Id
    @Column(name = "pf_save_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance pfNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

}
