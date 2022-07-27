package com.troupe.backend.domain.character;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_character_eye")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CharacterEye implements Serializable {
    @Id
    private Integer eyeNo;

    private String eyeUrl;
}
