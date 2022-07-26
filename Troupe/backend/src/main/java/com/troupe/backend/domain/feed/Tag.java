package com.troupe.backend.domain.feed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_tag")
public class Tag implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagNo;

    private String name;
}