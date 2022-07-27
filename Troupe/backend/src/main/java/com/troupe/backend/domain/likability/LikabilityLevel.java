package com.troupe.backend.domain.likability;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class LikabilityLevel implements Serializable {
    @Id
    private Integer level;

    private Integer requiredExp;
}
