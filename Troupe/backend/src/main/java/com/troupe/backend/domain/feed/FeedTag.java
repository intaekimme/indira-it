package com.troupe.backend.domain.feed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_feed_tag")
public class FeedTag implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  int tagNo;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  int feedNo;
}