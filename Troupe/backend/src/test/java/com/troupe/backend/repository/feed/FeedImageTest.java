package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.character.*;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import com.troupe.backend.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FeedImageTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    FeedImageRepository feedImageRepository;

    @Test
    @DisplayName("해당 피드 번호에 해당하는 사진 넣기")
    public void insert() throws ParseException {
        Feed feed = feedRepository.findById(3).get();
        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
        feedImageRepository.save(saveImage);
        Assertions.assertThat(saveImage.getImageUrl()).isEqualTo("url");
    }

    @Test
    @DisplayName("feed_image table에 있는 모든 image 불러오기")
    public void selectAll() {
        //추가
        Feed feed = feedRepository.findById(3).get();
        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
        feedImageRepository.save(saveImage);
        //list 불러오기
        List<FeedImage> feeds = feedImageRepository.findAll();
        Assertions.assertThat(feeds.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("해당 feed_no에 해당하는 모든 image 불러오기")
    public void selectById() throws ParseException{
//        insert();
        //추가
        Feed feed = feedRepository.findById(3).get();
        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
        feedImageRepository.save(saveImage);

        List<FeedImage> feeds = feedImageRepository.findAllByFeedNo(saveImage.getFeed());

        Assertions.assertThat(feeds.size()).isGreaterThan(0);
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
