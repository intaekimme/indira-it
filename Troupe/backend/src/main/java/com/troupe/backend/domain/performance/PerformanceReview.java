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
import java.util.List;

@Builder
@Getter
@Table(name = "tb_pf_review")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview implements Serializable {

    @Id
    @Column(name = "review_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pf_no")
    private Performance pf;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private boolean isModified;

    private boolean isRemoved;

    @Size(max = 5000)
    private String content;

    // 자기참조 부모 하나
    @ManyToOne(targetEntity = PerformanceReview.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_review_no")
    private PerformanceReview parentPerformanceReview;

    // 자기참조 자식 여러개
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPerformanceReview")
    private List<PerformanceReview> childrenPerformanceReview;

    public boolean getRemoved(){
        return this.isRemoved;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPfNo(Performance pf) {
        this.pf = pf;
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
