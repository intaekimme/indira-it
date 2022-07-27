package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.FeedSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedSaveRepository extends JpaRepository<FeedSave, Integer> {
}