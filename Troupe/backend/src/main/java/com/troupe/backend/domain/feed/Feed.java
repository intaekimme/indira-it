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
@Table(name = "tb_feed")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedNo;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;
    private String content;
    private boolean isRemoved;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
}
