package com.troupe.backend.domain.likability;

import com.troupe.backend.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability_level")
@Getter
public class LikabilityLevel implements Serializable {
    @Id
    private Integer level;

    private Integer requiredExp;
}
