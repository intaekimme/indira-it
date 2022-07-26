package com.troupe.backend.domain.member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_member_guestbook")
public class GuestBook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int guestbookNo;

    @ManyToOne
    @JoinColumn(name = "host_member_no")
    private Member hostMember;

    @ManyToOne
    @JoinColumn(name = "visitor_member_no")
    private Member visitorMember;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    private String content;

    boolean isRemoved;
}
