package com.troupe.backend.domain.report;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import lombok.Getter;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_report")
@Getter
public class Report implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no")
    private Comment comment;

    @ManyToOne(targetEntity = Feed.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance performance;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

}