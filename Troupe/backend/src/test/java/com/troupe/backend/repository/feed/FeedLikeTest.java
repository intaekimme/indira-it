package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FeedLikeTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FeedLikeRepository feedLikeRepository;

    @Test
    @DisplayName("피드 좋아요한적 없을때 좋아요 추가 및 있다면 좋아요 스위칭")
    public void insert() {
//        feedLikeRepository.save(FeedLike.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).build());
        Optional<FeedLike> feedLike = feedLikeRepository.findByMemberAndFeed(memberRepository.findById(3).get(),feedRepository.findById(3).get());
        FeedLike feedLike1 = null;
        if(feedLike.isEmpty()){
            feedLike1 = feedLikeRepository.save(FeedLike.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).build());
        }else{
            // 좋아요 스위칭
            boolean check = feedLike.get().isDeleted();
            feedLike1 = feedLikeRepository.save(FeedLike.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).isDeleted(!check).build());

        }
        // 최초 좋아요 시
        Assertions.assertThat(feedLike1.isDeleted()).isEqualTo(false);
        // 이미 좋아요 했을 시 취소
//        Assertions.assertThat(feedLike1.isDeleted()).isEqualTo(true);
    }

    @Test
    public void test(){
        Feed feed = feedRepository.findById(26).get();
        int total= feedLikeRepository.findByFeedAndIsDeletedFalse(feed);
        Assertions.assertThat(total).isEqualTo(1);
    }

}
