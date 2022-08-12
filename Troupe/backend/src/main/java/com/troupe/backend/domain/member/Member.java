package com.troupe.backend.domain.member;

import com.troupe.backend.domain.avatar.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_member")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements UserDetails, Serializable {
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

    private boolean isAuthenticatedEmail;

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

    /**
     * memberNo 빼고 전부 받는 생성자 (테스트용)
     */
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

    /**
     * 이메일, 닉네임만 받는 생성자 (테스트용)
     */
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

    // Spring Security 사용을 위해 UserDetails 상속받아 메소드 오버라이딩
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> ret = new ArrayList<>();
        ret.add(new SimpleGrantedAuthority(this.memberType.name()));
        return ret;
    }

    /**
     * userName은 PK인 memberNo를 의미하도록 한다
     */
    @Override
    public String getUsername() {
        return String.valueOf(this.memberNo);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !(this.isRemoved);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(this.isRemoved);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !(this.isRemoved);
    }

    @Override
    public boolean isEnabled() {
        return !(this.isRemoved);
    }

    public List<String> getRoles() {
        List<String> ret = new ArrayList<>();

        ret.add(this.memberType.name());

        return ret;
    }
}
