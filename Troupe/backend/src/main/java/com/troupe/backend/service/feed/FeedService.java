package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.dto.converter.FeedEntityToDto;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.repository.feed.FeedImageRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.feed.FeedTagRepository;
import com.troupe.backend.repository.feed.TagRepository;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    @Autowired
    FeedRepository feedRepository;

    @Autowired
    FeedImageRepository feedImageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    FeedTagRepository feedTagRepository;


    // 피드 등록
    public void insert(FeedInsertRequest request)throws Exception{
        try {
            memberRepository.findById(request.getMemberNo());
            // 피드 본문
            Feed newFeed = feedRepository.save(request.toFeedEntity());
            if(newFeed!=null)  System.out.println("newFeedNumber:  "+newFeed.getContent());
            else   System.out.println("new Feed null");
            // 피드 이미지
            for(FeedImage image: request.toFeedImageEntity(newFeed)){
                feedImageRepository.save(image);
            }

            //피드 태그
            for(Tag tag: request.toTagEntity()){
                Optional<Tag> tagInsert = tagRepository.findByName(tag.getName());
                if(tagInsert.isPresent()){
                    tagRepository.save(tag);
                }
                feedTagRepository.save(FeedTag.builder().tag(tag).feed(newFeed).build());
            }

        }catch (Exception e){
            log.info(e.toString());
        }
    }
}
