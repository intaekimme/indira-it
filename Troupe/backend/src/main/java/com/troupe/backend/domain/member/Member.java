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

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public void setClothes(CharacterClothes clothes) {
        this.clothes = clothes;
    }

    public void setEye(CharacterEye eye) {
        this.eye = eye;
    }

    public void setHair(CharacterHair hair) {
        this.hair = hair;
    }

    public void setMouth(CharacterMouth mouth) {
        this.mouth = mouth;
    }

    public void setNose(CharacterNose nose) {
        this.nose = nose;
    }

    public void setShape(CharacterShape shape) {
        this.shape = shape;
    }
}
