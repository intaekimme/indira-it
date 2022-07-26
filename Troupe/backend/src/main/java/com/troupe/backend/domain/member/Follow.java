package com.troupe.backend.domain.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_follow")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Follow implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fan_member_no")
    private Member fanMember;

}
