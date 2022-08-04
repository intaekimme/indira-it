package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedTagRepository extends JpaRepository<FeedTag, Integer> {
    List<FeedTag> findAllByFeed(Feed feed);
    List<FeedTag> findAllByTag(Tag tag);

    @Query(value = "select distinct count(*) as count, t.feed_no, t.feed_tag_no, t.tag_no from tb_feed f join tb_feed_tag t  on f.feed_no = t.feed_no join tb_tag tt  on t.tag_no = tt.tag_no "
    +" where tt.name in(:tags)  "
    +" group by f.feed_no having count = :size order by f.created_time desc ",nativeQuery = true)
    List<FeedTag> findAllByTagsIn(@Param("tags") List<String> tags, @Param("size") int size);

    FeedTag findByFeedAndTag(Feed feed, Tag tag);
}