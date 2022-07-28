package com.troupe.backend.domain.avatar;

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
public class AvatarEye implements Serializable {
    @Id
    private Integer eyeNo;

    private String eyeUrl;
}
