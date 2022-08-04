package com.troupe.backend.domain.category;


import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Table(name = "tb_interest_category")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InterestCategory implements Serializable {

    @Id
    @Column(name = "interest_category_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;
}
