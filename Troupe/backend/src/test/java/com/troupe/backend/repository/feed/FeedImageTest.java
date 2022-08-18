package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
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

//    @Test
//    @DisplayName("해당 피드 번호에 해당하는 사진 넣기")
//    public void insert() throws ParseException {
//        Feed feed = feedRepository.findById(3).get();
//        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
//        feedImageRepository.save(saveImage);
//        Assertions.assertThat(saveImage.getImageUrl()).isEqualTo("url");
//    }
//
//    @Test
//    @DisplayName("feed_image table에 있는 모든 image 불러오기")
//    public void selectAll() {
//        //추가
//        Feed feed = feedRepository.findById(3).get();
//        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
//        feedImageRepository.save(saveImage);
//        //list 불러오기
//        List<FeedImage> feeds = feedImageRepository.findAll();
//        Assertions.assertThat(feeds.size()).isGreaterThan(0);
//    }
//
//    @Test
//    @DisplayName("해당 feed_no에 해당하는 모든 image 불러오기")
//    public void selectAllById() throws ParseException{
////        insert();
//        //추가
//        Feed feed = feedRepository.findById(3).get();
//        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
//        feedImageRepository.save(saveImage);
//
//        List<FeedImage> feeds = feedImageRepository.findAllByFeedOrderByImageNo(saveImage.getFeed());
//
//        Assertions.assertThat(feeds.size()).isGreaterThan(0);
//    }
//
//    @Test
//    @DisplayName("해당 피드의 사진 하나 찾아서 사진교체하기")
//    public void updateFeedImage() {
//        // 삽입
//        Feed feed = feedRepository.findById(3).get();
//        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
//        feedImageRepository.save(saveImage);
//
//        // 해당 사진 찾기
//        FeedImage selectImage = feedImageRepository.findByFeedAndImageUrl(feed,"url");
//        FeedImage updateFeed =  selectImage.builder().feed(feed).imageUrl("update").build();
//
//        // 사진 교체 저장
//        FeedImage updateImage = feedImageRepository.save(updateFeed);
//        Assertions.assertThat(updateImage.getImageUrl()).isEqualTo("update");
//    }
//    @Test
//    @DisplayName("해당 피드의 사진 하나 찾아서 삭제하기")
//    public void deleteFeedImage(){
//        // 삽입
//        Feed feed = feedRepository.findById(3).get();
//        FeedImage saveImage = FeedImage.builder().feed(feed).imageUrl("url").build();
//        feedImageRepository.save(saveImage);
//
//        // 해당 사진 찾기
//        FeedImage selectImage = feedImageRepository.findByFeedAndImageUrl(feed,"url");
//        feedImageRepository.delete(selectImage);
//
//        FeedImage feedImage1 = null;
//
//        Optional<FeedImage> feedImage = feedImageRepository.findByImageUrl("url");
//
//        if(feedImage.isPresent()) feedImage1 = feedImage.get();
//
//        Assertions.assertThat(feedImage1).isNull();
//    }
}
