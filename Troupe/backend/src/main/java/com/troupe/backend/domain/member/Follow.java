package com.troupe.backend.domain.member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_follow")
public class Follow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int followNo;

    @ManyToOne
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne
    @JoinColumn(name = "fan_member_no")
    private Member fanMember;

}
