package com.troupe.backend.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_tag")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagNo;

    private String name;
}