package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.repository.feed.FeedTagRepository;
import com.troupe.backend.repository.feed.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    FeedTagRepository feedTagRepository;


    public void insert(List<Tag> tags, Feed feed){
//        System.out.println("tagListsize:  "+ tags.size());
        try {
            for(Tag tag: tags){
                Optional<Tag> tagInsert = tagRepository.findByName(tag.getName());
                if(tagInsert.isEmpty()){
                    tagRepository.save(tag);
                }
                feedTagRepository.save(FeedTag.builder().tag(tag).feed(feed).build());
            }

        }catch (Exception e){
            log.info(e.toString());
        }
    }
    public void update(Feed feed){
//        feedTagRepository.save(FeedTag.builder().feedTagNo())
    }
    public void delete(FeedTag feedtag){
        try {
            feedTagRepository.delete(feedtag);
        }catch (Exception e){
            log.info(e.toString());
        }
    }
    public FeedTag selectFeedTag (Feed feed, Tag tag){
        return feedTagRepository.findByFeedAndTag(feed,tag);
    }
    public List<Tag> selectTags(List<Integer> taglist){
        List<Tag> list = new ArrayList<>();
        for(Integer no:taglist){
            list.add(tagRepository.findById(no).get());
        }
        return list;
    }
}
