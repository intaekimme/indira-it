package com.troupe.backend.domain.character;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_character_eye")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterEye implements Serializable {
    @Id
    private Integer eyeNo;

    private String eyeUrl;
}
