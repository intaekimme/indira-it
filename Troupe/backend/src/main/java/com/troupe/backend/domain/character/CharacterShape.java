package com.troupe.backend.domain.character;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_character_shape")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CharacterShape implements Serializable {
    @Id
    private Integer shapeNo;

    private String shapeUrl;
}
