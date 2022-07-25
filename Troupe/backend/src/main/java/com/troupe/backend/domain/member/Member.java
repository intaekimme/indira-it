package com.troupe.backend.domain.member;

import javax.persistence.*;

@Entity
@Table(name = "tb_member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer memberNo;

    String email;

    String password;

    String nickname;

    String description;

    @Enumerated(EnumType.STRING)
    MemberType memberType;

    String profileImageUrl;

    boolean isRemoved;

    // hello world
}
