package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
//    Optional<Feed> findById(int feedNo);

    Optional<List<Feed>> findAllByIsRemovedOrderByCreatedTimeDesc(boolean isRemoved);
    List<Feed> findAllByMemberOrderByCreatedTimeDesc(Member member);

}