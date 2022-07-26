package com.troupe.backend.domain.category;


import com.troupe.backend.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "tb_interest_category")
@Entity
public class InterestCategory {

    @Id
    @Column(name = "interest_category_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "code_no")
    private Category codeNo;
}
