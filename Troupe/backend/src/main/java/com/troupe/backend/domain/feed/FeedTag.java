package com.troupe.backend.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_feed_tag")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedTag implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  int feedTagNo;

    @ManyToOne(targetEntity = Tag.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no")
    private  Tag tag;

    @ManyToOne(targetEntity = Feed.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private  Feed feed;
}