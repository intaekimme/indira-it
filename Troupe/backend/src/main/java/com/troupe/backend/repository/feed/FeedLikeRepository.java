package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Integer> {
    Optional<FeedLike> findByMemberAndFeed(Member member, Feed feed);
    //    @Query(value = "select count(feed_no) from tb_feed_like group by feed_no having is_deleted=false ")
//    FeedLike FeedLikeCount(int feedNo);

}