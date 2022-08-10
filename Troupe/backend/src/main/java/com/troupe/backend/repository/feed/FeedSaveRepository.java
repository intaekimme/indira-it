package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedSave;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedSaveRepository extends JpaRepository<FeedSave, Integer> {

    Optional<FeedSave> findByMemberAndFeed(Member member, Feed feed);

    Optional<FeedSave> findByMemberAndFeedAndIsDeletedFalse(Member member, Feed feed);

    Optional<Slice<FeedSave>> findAllByMemberAndIsDeletedOrderByCreatedTimeDesc(Member member, boolean check, Pageable pageable);

    List<FeedSave> findAllByMemberAndIsDeleted(Member member, boolean isDeleted);
}