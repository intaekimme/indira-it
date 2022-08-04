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


@Getter
@Builder
@Entity
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
    private Date startDate;
    private Date endDate;

    private boolean isRemoved;

    public boolean getRemoved(){
        return this.isRemoved;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setCodeNo(Integer codeNo) {
        this.codeNo = codeNo;
    }

    public void setDetailTime(String detailTime) {
        this.detailTime = detailTime;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Performance{" +
                "id=" + id +
                ", memberNo=" + memberNo +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", runtime=" + runtime +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", posterUrl='" + posterUrl + '\'' +
                ", codeNo=" + codeNo +
                ", detailTime='" + detailTime + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isRemoved=" + isRemoved +
                '}';
    }
}
