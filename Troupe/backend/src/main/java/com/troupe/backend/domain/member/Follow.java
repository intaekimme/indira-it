package com.troupe.backend.domain.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_follow")
@Getter
public class Follow implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer followNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fan_member_no")
    private Member fanMember;

}
