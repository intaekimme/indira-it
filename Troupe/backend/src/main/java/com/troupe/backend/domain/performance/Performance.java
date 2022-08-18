package com.troupe.backend.domain.performance;

import com.troupe.backend.domain.category.Category;
import com.troupe.backend.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Getter
@Builder
@Entity
@Setter
@Table(name = "tb_performance")
@NoArgsConstructor
@AllArgsConstructor
public class Performance implements Serializable{

    @Id
    @Column(name = "pf_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

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

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    private String detailTime;
    private Date startDate;
    private Date endDate;

    private boolean isRemoved;

    @Override
    public String toString() {
        return "Performance{" +
                "id=" + id +
                ", memberNo=" + member +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", runtime=" + runtime +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", posterUrl='" + posterUrl + '\'' +
                ", category=" + category +
                ", detailTime='" + detailTime + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isRemoved=" + isRemoved +
                '}';
    }
}
