package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import com.troupe.backend.repository.member.MemberRepository;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FeedTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void insert() throws ParseException {
//        AvatarClothes clothes = new AvatarClothes(1,"url");
//        AvatarEye eyes = new AvatarEye(1,"url");
//        AvatarHair hair = new AvatarHair(1,"url");
//        AvatarMouth mouth = new AvatarMouth(1,"url");
//        AvatarNose nose = new AvatarNose(1,"url");
//        AvatarShape shape = new AvatarShape(1,"url");
//        Member member  = new Member(3,"email","password","nickname","description", MemberType.PERFORMER,"url",false, clothes,eyes,hair,mouth,nose,shape);
//        Feed feed = new Feed(1,member,"insertTest",false,null);
//        Feed feedSave = feedRepository.save(feed);
//        Assertions.assertThat(feedSave.getContent()).isEqualTo("insertTest");
//        Assertions.assertEquals("insertTest",feedSave.getContent());
//        Member member = memberRepository.getById(3);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        Feed feed = Feed.builder().member(member).content("content").isRemoved(false).build();
        Feed feedSave =  feedRepository.save(Feed.builder().member(memberRepository.getById(3)).content("content").isRemoved(false).build());
        Assertions.assertThat(feedSave.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("피드 최신순 정렬")
    public void selectAll() {
        List<Feed> feeds = feedRepository.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));

        Assertions.assertThat(feeds.size()).isGreaterThan(0);
        Assertions.assertThat(feeds.get(0).getFeedNo()).isEqualTo(5);
    }

    @Test
    @DisplayName("피드 등록한 사람의 피드 목록 최신순 정렬")
    public void selectAllByPerformer() {
        List<Feed> feeds = feedRepository.findByMemberOrderByCreatedTimeDesc(memberRepository.getById(3));

        Assertions.assertThat(feeds.size()).isEqualTo(3);
        Assertions.assertThat(feeds.get(0).getFeedNo()).isEqualTo(5);
    }

    @Test
    public void selectById(){
        Feed feed = feedRepository.findById(3).get();

        Assertions.assertThat(feed.getFeedNo()).isEqualTo(3);
    }

    @Test
    public void updateFeed(){
        Feed feed = feedRepository.findById(3).get();
        Feed updateFeed = Feed.builder().member(feed.getMember()).content("update").build();
        Feed saveFeed = feedRepository.save(updateFeed);
        Assertions.assertThat(saveFeed.getContent()).isEqualTo("update");
    }
    @Test
    public void deleteFeed(){
        Feed feed = feedRepository.findById(3).get();
        feedRepository.delete(feed);

        Feed feed1 = null;

        Optional<Feed> feeds = feedRepository.findById(3);

        if(feeds.isPresent()){
            feed1 = feeds.get();
        }

        Assertions.assertThat(feed1).isNull();
    }
}
