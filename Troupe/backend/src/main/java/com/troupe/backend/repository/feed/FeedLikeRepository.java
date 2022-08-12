package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Integer> {
        Optional<FeedLike> findByMemberAndFeed(Member member, Feed feed);

        Optional<FeedLike> findByMemberAndFeedAndIsDeletedFalse(Member member, Feed feed);
        @Query(value = "select count(*) from tb_feed_like fl "
                +" where fl.feed_no = :feed and fl.is_deleted = false "
                + "group by fl.feed_no = :feed ", nativeQuery = true)
        Integer findByFeedAndIsDeletedFalse (Feed feed);
}