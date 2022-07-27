package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
    Optional<Feed> findById(int feedNo);
}