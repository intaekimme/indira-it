package com.troupe.backend.domain.likability;

import com.troupe.backend.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Likability implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likabilityNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fan_member_no")
    private Member fanMember;

    private Integer exp;

    public void setLikabilityNo(Integer likabilityNo) {
        this.likabilityNo = likabilityNo;
    }

    public void setStarMember(Member starMember) {
        this.starMember = starMember;
    }

    public void setFanMember(Member fanMember) {
        this.fanMember = fanMember;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
