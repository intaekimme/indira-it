package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.FeedConverter;
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

import java.util.ArrayList;
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

    @Autowired
    FeedConverter converter;

    // 피드 등록
    public void insert(FeedInsertRequest request) throws Exception{
        try {
            // 현재 로그인 한 멤버번호 가져오기
             Optional<Member> member =  memberRepository.findById(request.getMemberNo());
            // 피드 본문
            Feed newFeed = feedRepository.save(request.toFeedEntity(member.get()));

            List<FeedImage> feedImageList =  converter.toFeedImageEntity(newFeed,request.getImages());
            System.out.println("feedImageListsize:  "+ feedImageList.size());
            
            // 피드 이미지들 저장
            for(FeedImage image: feedImageList){
                feedImageRepository.save(image);
            }

            List<Tag> tags = converter.toTagEntity(request.getTags());
            //피드 태그
            for(Tag tag: tags){
                Optional<Tag> tagInsert = tagRepository.findByName(tag.getName());
                if(tagInsert.isEmpty()){
                    tagRepository.save(tag);
                }
                feedTagRepository.save(FeedTag.builder().tag(tag).feed(newFeed).build());
            }

        }catch (Exception e){
            log.info(e.toString());
        }
    }
}
