package com.troupe.backend.domain.feed;

import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_feed_save")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedSaveNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(targetEntity = Feed.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private boolean isDeleted;

    public void setDeleted(boolean isDeleted){this.isDeleted = isDeleted; }
    public void setCreatedTime(Date createdTime){this.createdTime = createdTime;}
}