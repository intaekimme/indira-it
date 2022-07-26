package com.troupe.backend.domain.character;

import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_member_character")
@Getter
public class Character implements Serializable {
    @Id
    @OneToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;

    @ManyToOne(targetEntity = CharacterClothes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_no")
    CharacterClothes clothes;

    @ManyToOne(targetEntity = CharacterEye.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "eye_no")
    CharacterEye eye;

    @ManyToOne(targetEntity = CharacterHair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_no")
    CharacterHair hair;

    @ManyToOne(targetEntity = CharacterMouth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mouth_no")
    CharacterMouth mouth;

    @ManyToOne(targetEntity = CharacterNose.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nose_no")
    CharacterNose nose;

    @ManyToOne(targetEntity = CharacterShape.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shape_no")
    CharacterShape shape;
}