package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedTagRepository extends JpaRepository<FeedTag, Integer> {
}