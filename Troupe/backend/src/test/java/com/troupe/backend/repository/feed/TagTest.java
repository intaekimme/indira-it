package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TagTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    FeedImageRepository feedImageRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    FeedTagRepository feedTagRepository;

    @Test
    @DisplayName("해당 태그 있는지 확인하고 태그 삽입")
    public void insert() throws ParseException {

        // 삽입(원래데이터 취급)
        Tag tag = Tag.builder().name("test1").build();
        tagRepository.save(tag);

        // 지금 추가하려는 태그
        Optional<Tag> tagInsert1 = tagRepository.findByName("test1");
        Optional<Tag> tagInsert2 = tagRepository.findByName("test2");

        //list test2가 들어가야함
        List<Tag> saveTag = new ArrayList<>();
        if(tagInsert1.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test1").build());
            saveTag.add(newTag);
        }
        if(tagInsert2.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test2").build());
            saveTag.add(newTag);
        }

        // 사이즈 1
        Assertions.assertThat(saveTag.size()).isEqualTo(1);
        Assertions.assertThat(saveTag.get(0).getName()).isEqualTo("test2");
    }

    @Test
    @DisplayName("해당 피드의 tag들 불러오기")
    public void selectAll() {
        // 지금 추가하려는 태그
        Optional<Tag> tagInsert1 = tagRepository.findByName("test1");
        Optional<Tag> tagInsert2 = tagRepository.findByName("test2");
        List<Tag> saveTag = new ArrayList<>();
        if(tagInsert1.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test1").build());
            saveTag.add(newTag);
        }
        if(tagInsert2.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test2").build());
            saveTag.add(newTag);
        }

        //피드 찾고 feedTag 추가
        Feed feed = feedRepository.findById(3).get();
        List<FeedTag> feedTags = new ArrayList<>();
        feedTags.add(FeedTag.builder().feed(feed).tag(tagRepository.findByName(saveTag.get(0).getName()).get()).build());
        feedTags.add(FeedTag.builder().feed(feed).tag(tagRepository.findByName(saveTag.get(1).getName()).get()).build());
        feedTagRepository.saveAll(feedTags);

        // 해당 피드들의 tag들 불러오기
        List<FeedTag> tags = feedTagRepository.findAllByFeed(feed);

        // 사이즈 2
        Assertions.assertThat(tags.size()).isEqualTo(2);
        Assertions.assertThat(tags.get(0).getTag().getName()).isEqualTo(saveTag.get(0).getName());
        Assertions.assertThat(tags.get(1).getTag().getName()).isEqualTo(saveTag.get(1).getName());
    }

    @Test
    @DisplayName("태그 검색")
    public void searchTag(){
        // 지금 추가하려는 태그
        Optional<Tag> tagInsert1 = tagRepository.findByName("test1");
        Optional<Tag> tagInsert2 = tagRepository.findByName("test2");
        List<Tag> saveTag = new ArrayList<>();
        if(tagInsert1.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test1").build());
            saveTag.add(newTag);
        }
        if(tagInsert2.isEmpty()) {
            Tag newTag = tagRepository.save(Tag.builder().name("test2").build());
            saveTag.add(newTag);
        }

        //피드 찾고 feedTag 추가
        Feed feed = feedRepository.findById(3).get();
        Feed feed2 = feedRepository.findById(4).get();
        List<FeedTag> feedTags = new ArrayList<>();
        feedTags.add(FeedTag.builder().feed(feed).tag(tagRepository.findByName(saveTag.get(0).getName()).get()).build());
        feedTags.add(FeedTag.builder().feed(feed).tag(tagRepository.findByName(saveTag.get(1).getName()).get()).build());

        feedTags.add(FeedTag.builder().feed(feed2).tag(tagRepository.findByName(saveTag.get(0).getName()).get()).build());
        feedTagRepository.saveAll(feedTags);

        // 여기부터 실제 로직
        // test1 태그 검색
        List<FeedTag> feeds = feedTagRepository.findAllByTag(tagRepository.findByName(saveTag.get(0).getName()).get());
        // test2 태그 검색
        List<FeedTag> feeds2 = feedTagRepository.findAllByTag(tagRepository.findByName(saveTag.get(1).getName()).get());

        // test1 test2 둘다 검색
        List<FeedTag> feeds3 = feedTagRepository.findAllByTagIn(saveTag);

        // 3번, 4번 피드에 test1 있어서 검색됨
        Assertions.assertThat(feeds.size()).isEqualTo(2);
        Assertions.assertThat(feeds.get(0).getFeed().getFeedNo()).isEqualTo(3);
        Assertions.assertThat(feeds.get(1).getFeed().getFeedNo()).isEqualTo(4);

        //4번 피드에 test2 없어서 검색 안됨
        Assertions.assertThat(feeds2.size()).isEqualTo(1);
        Assertions.assertThat(feeds2.get(0).getFeed().getFeedNo()).isEqualTo(3);

        // test1 test2 둘중에 하나라도 있는 피드 검색(일단 보류)
        HashSet<Integer> set = new HashSet<>();
        set.add(feeds3.get(0).getFeed().getFeedNo());
        set.add(feeds3.get(1).getFeed().getFeedNo());
        set.add(feeds3.get(2).getFeed().getFeedNo());

        Assertions.assertThat(set.size()).isEqualTo(2);
        Assertions.assertThat(set.contains(3));
        Assertions.assertThat(set.contains(4));
        Assertions.assertThat(set.contains(5)).isFalse();
//        Assertions.assertThat(feeds3.get(0).getFeed().getFeedNo()).isEqualTo(3);
    }

}
