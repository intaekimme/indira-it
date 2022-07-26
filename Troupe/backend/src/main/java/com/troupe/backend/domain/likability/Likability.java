package com.troupe.backend.domain.likability;

import com.troupe.backend.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability")
public class Likability {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int likabilityNo;

    @ManyToOne
    @JoinColumn(name = "star_member_no")
    Member starMember;

    @ManyToOne
    @JoinColumn(name = "fan_member_no")
    Member fanMember;

    int exp;
}
