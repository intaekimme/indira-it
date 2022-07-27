package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Integer> {
}