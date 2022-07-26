package com.troupe.backend.domain.notification;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_notification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeNo;

    @ManyToOne(targetEntity = Comment.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(targetEntity = Performance.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Performance performance;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "star_member_no")
    private Member starMember;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no")
    private Comment comment;

    private String type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date readTime;

}