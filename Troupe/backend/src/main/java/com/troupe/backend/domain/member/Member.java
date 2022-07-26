package com.troupe.backend.domain.member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    private String email;

    private String password;

    private String nickname;

    private String description;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String profileImageUrl;

    private boolean isRemoved; //
}
