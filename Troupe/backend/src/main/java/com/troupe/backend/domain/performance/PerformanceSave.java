package com.troupe.backend.domain.performance;


import com.troupe.backend.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
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
    private Member member;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private boolean isRemoved;

    @Override
    public String toString() {
        return "PerformanceSave{" +
                "id=" + id +
                ", memberNo=" + member.getMemberNo() +
                ", pfNo=" + pf.getId() +
                ", createdTime=" + createdTime +
                ", isRemoved=" + isRemoved +
                '}';
    }
}
