package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface FeedImageRepository extends JpaRepository<FeedImage, Integer> {
    // 해당 feed에 해당하는 이미지 리스트 불러오기
    List<FeedImage> findAllByFeedOrderByImageNo(Feed feed);

    // 해당 피드의 사진 하나 찾아서 사진교체하기
    FeedImage findByFeedAndImageUrl(Feed feed, String url);

    // delete 시 해당 url 찾기
    Optional<FeedImage> findByImageUrl(String url);
}