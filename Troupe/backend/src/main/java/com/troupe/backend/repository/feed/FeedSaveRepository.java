package com.troupe.backend.repository.feed;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.feed.FeedSave;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedSaveRepository extends JpaRepository<FeedSave, Integer> {

    Optional<FeedSave> findByMemberAndFeed(Member member, Feed feed);

    Optional<List<FeedSave>> findAllByMemberAndIsDeletedOrderByCreatedTime(Member member, boolean check);
}