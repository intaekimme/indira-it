package com.troupe.backend.domain.performance;

import com.troupe.backend.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Table(name = "tb_pf_review")
@Entity
public class PerformanceReview {

    @Id
    @Column(name = "review_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pf_no")
    private Performance pfNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private boolean isModified;

    private boolean isRemoved;

    @Size(max = 5000)
    private String content;


}
