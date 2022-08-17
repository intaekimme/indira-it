package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.feed.FeedSave;
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
class FeedSaveTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FeedSaveRepository feedSaveRepository;

//    @Test
//    @DisplayName("피드 저장한적 없을때 저장 추가 및 있다면 저장 스위칭")
//    public void insert() {
////        feedSaveRepository.save(FeedSave.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).build());
//        Optional<FeedSave> feedSave = feedSaveRepository.findByMemberAndFeed(memberRepository.findById(3).get(),feedRepository.findById(3).get());
//        FeedSave feedSave1 = null;
//        if(feedSave.isEmpty()){
//            feedSave1 = feedSaveRepository.save(FeedSave.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).build());
//        }else{
//            // 저장 스위칭
//            boolean check = feedSave.get().isDeleted();
//            feedSave1 = feedSaveRepository.save(FeedSave.builder().member(memberRepository.findById(3).get()).feed(feedRepository.findById(3).get()).isDeleted(!check).build());
//
//        }
//        // 최초 저장 시
//        Assertions.assertThat(feedSave1.isDeleted()).isEqualTo(false);
//        // 이미 저장했을 시 취소
////        Assertions.assertThat(feedSave1.isDeleted()).isEqualTo(true);
//    }

}
