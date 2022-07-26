package com.troupe.backend.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_feed_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageNo;

    @ManyToOne(targetEntity = Feed.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;
    private String imageUrl;
}
