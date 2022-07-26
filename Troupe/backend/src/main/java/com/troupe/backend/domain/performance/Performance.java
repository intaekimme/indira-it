package com.troupe.backend.domain.performance;

import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Entity
@Table(name = "tb_performance")
@NoArgsConstructor
@AllArgsConstructor
public class Performance implements Serializable {

    @Id
    @Column(name = "pf_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member memberNo;

    private String title;

    private String location;

    private Integer runtime;

    @Size(max = 5000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Size(max = 1000)
    private String posterUrl;

    private Integer codeNo;

    private String detailTime;

    private boolean isRemoved;
    
}
