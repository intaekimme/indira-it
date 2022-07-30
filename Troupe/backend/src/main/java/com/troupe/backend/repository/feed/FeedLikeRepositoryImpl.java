package com.troupe.backend.repository.feed;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.troupe.backend.domain.feed.FeedLike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class FeedLikeRepositoryImpl implements FeedLikeRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public FeedLikeRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
     public FeedLike FeedLikeCount(int feedNo){
        QFeedLike qFeedLike = QFeedLike.feedLike;
        return jpaQueryFactory.selectFrom(qFeedLike)
                .groupBy(qFeedLike.feed.feedNo)
                .select(
                        Projections.bean(
                                FeedLike.class
                                ,qFeedLike.feed.feedNo.sum().as("totalSum")
                        )
                ).where(qFeedLike.isDeleted.eq(false)).fetchOne();
    }
}
