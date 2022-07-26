package com.troupe.backend.domain.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_member_guestbook")
@Getter
public class Guestbook implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestbookNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "host_member_no")
    private Member hostMember;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_member_no")
    private Member visitorMember;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private String content;

    private boolean isRemoved;
}
