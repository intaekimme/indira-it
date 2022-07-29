package com.troupe.backend.domain.member;

import com.troupe.backend.domain.avatar.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;

    private String email;

    private String password;

    private String nickname;

    private String description;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String profileImageUrl;

    private boolean isRemoved;

    @ManyToOne(targetEntity = AvatarClothes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_no")
    private AvatarClothes clothes;

    @ManyToOne(targetEntity = AvatarEye.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "eye_no")
    private AvatarEye eye;

    @ManyToOne(targetEntity = AvatarHair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_no")
    private AvatarHair hair;

    @ManyToOne(targetEntity = AvatarMouth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mouth_no")
    private AvatarMouth mouth;

    @ManyToOne(targetEntity = AvatarNose.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nose_no")
    private AvatarNose nose;

    @ManyToOne(targetEntity = AvatarShape.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shape_no")
    private AvatarShape shape;

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

    public void setClothes(AvatarClothes clothes) {
        this.clothes = clothes;
    }

    public void setEye(AvatarEye eye) {
        this.eye = eye;
    }

    public void setHair(AvatarHair hair) {
        this.hair = hair;
    }

    public void setMouth(AvatarMouth mouth) {
        this.mouth = mouth;
    }

    public void setNose(AvatarNose nose) {
        this.nose = nose;
    }

    public void setShape(AvatarShape shape) {
        this.shape = shape;
    }

    /** memberNo 빼고 전부 받는 생성자 */
    public Member(String email, String password, String nickname, String description, MemberType memberType, String profileImageUrl, boolean isRemoved, AvatarClothes clothes, AvatarEye eye, AvatarHair hair, AvatarMouth mouth, AvatarNose nose, AvatarShape shape) {
        this.memberNo = 1;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
        this.memberType = memberType;
        this.profileImageUrl = profileImageUrl;
        this.isRemoved = isRemoved;
        this.clothes = clothes;
        this.eye = eye;
        this.hair = hair;
        this.mouth = mouth;
        this.nose = nose;
        this.shape = shape;
    }

    /** 이메일, 닉네임만 받는 생성자 */
    public Member(String email, String nickname) {
        this(email, "mypassword", nickname, "mydescription", MemberType.AUDIENCE, "myprofileImageUrl", false,
                new AvatarClothes(1, "url"),
                new AvatarEye(1, "url"),
                new AvatarHair(1, "url"),
                new AvatarMouth(1, "url"),
                new AvatarNose(1, "url"),
                new AvatarShape(1, "url"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberNo.equals(member.memberNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberNo);
    }
}
