package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
//    Optional<Feed> findById(int feedNo);

    Optional<Slice<Feed>> findAllByIsRemovedOrderByCreatedTimeDesc(boolean isRemoved, Pageable pageable);
    Slice<Feed> findAllByMemberAndIsRemovedOrderByCreatedTimeDesc(Member member, boolean isRemoved, Pageable pageable);

    Slice<Feed> findAllByIsRemovedAndMemberInOrderByCreatedTimeDesc(boolean isRemoved,List<Member> member, Pageable pageable);
}