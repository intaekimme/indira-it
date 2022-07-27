package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}