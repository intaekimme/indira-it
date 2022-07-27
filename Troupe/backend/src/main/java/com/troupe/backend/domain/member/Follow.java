package com.troupe.backend.domain.member;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_follow")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fan_member_no")
    private Member fanMember;

    public void setStarMember(Member starMember) {
        this.starMember = starMember;
    }

    public void setFanMember(Member fanMember) {
        this.fanMember = fanMember;
    }
}
