package com.troupe.backend.domain.feed;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_feed_save")
@Getter
public class FeedSave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
}