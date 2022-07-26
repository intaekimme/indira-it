package com.troupe.backend.domain.comment;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_comment")
@Getter
public class Comment  implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;

    @ManyToOne(targetEntity = Feed.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    // 자기참조 부모 하나
    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment_no")
    private Comment parentComment;

    // 자기참조 자식 여러개
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment")
    private List<Comment> childrenComment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private boolean isModified;
    private boolean isRemoved;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;
}