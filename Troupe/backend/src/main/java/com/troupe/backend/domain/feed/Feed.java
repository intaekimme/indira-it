package com.troupe.backend.domain.feed;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_feed")
@Getter
public class Feed implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedNo;

    private int memberNo;
    private String content;
    private boolean isRemoved;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
}
