package com.troupe.backend.domain.likability;

import com.troupe.backend.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_likability_level")
public class LikabilityLevel implements Serializable {
    @Id
    int level;

    int requiredExp;
}
