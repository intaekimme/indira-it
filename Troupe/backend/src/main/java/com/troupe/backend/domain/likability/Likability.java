package com.troupe.backend.domain.likability;

import com.troupe.backend.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability")
@Getter
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
}
