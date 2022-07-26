package com.troupe.backend.domain.category;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Table(name = "tb_category")
@Entity
public class Category {

    @Id
    @Column(name = "code_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 20)
    private String bigCategory;

    @Size(max = 20)
    private String smallCategory;

    @Size(max = 10)
    private String codeName;
}
