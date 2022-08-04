package com.troupe.backend.domain.category;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "tb_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category implements Serializable {

    @Id
    @Column(name = "category_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 20)
    private String bigCategory;

    @Size(max = 20)
    private String smallCategory;

    @Size(max = 10)
    private String codeName;
}
