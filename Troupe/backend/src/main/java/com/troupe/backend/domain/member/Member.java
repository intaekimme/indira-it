package com.troupe.backend.domain.member;

import com.troupe.backend.domain.character.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;

    private String email;

    private String password;

    private String nickname;

    private String description;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String profileImageUrl;

    private boolean isRemoved;

    @ManyToOne(targetEntity = CharacterClothes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_no")
    private CharacterClothes clothes;

    @ManyToOne(targetEntity = CharacterEye.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "eye_no")
    private CharacterEye eye;

    @ManyToOne(targetEntity = CharacterHair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_no")
    private CharacterHair hair;

    @ManyToOne(targetEntity = CharacterMouth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mouth_no")
    private CharacterMouth mouth;

    @ManyToOne(targetEntity = CharacterNose.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nose_no")
    private CharacterNose nose;

    @ManyToOne(targetEntity = CharacterShape.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shape_no")
    private CharacterShape shape;

}
