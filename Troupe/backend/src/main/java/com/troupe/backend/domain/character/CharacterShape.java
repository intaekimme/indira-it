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
@Table(name = "tb_character_shape")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterShape implements Serializable {
    @Id
    private Integer shapeNo;

    private String shapeUrl;
}
