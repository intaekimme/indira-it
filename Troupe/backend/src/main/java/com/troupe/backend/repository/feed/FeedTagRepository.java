package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface FeedTagRepository extends JpaRepository<FeedTag, Integer> {
    List<FeedTag> findAllByFeed(Feed feed);
    List<FeedTag> findAllByTag(Tag tag);
    
    // 다중 태그 검색 or(후에 querydsl로 교체..)
    List<FeedTag> findAllByTagIn(List<Tag> tags);
    FeedTag findByFeedAndTag(Feed feed, Tag tag);
}