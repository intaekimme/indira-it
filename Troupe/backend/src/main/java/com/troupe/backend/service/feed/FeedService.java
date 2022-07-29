package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.FeedConverter;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.repository.feed.FeedRepository;
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
    MemberRepository memberRepository;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    TagService tagService;

    @Autowired
    FeedImageService feedImageService;

    @Autowired
    FeedConverter converter;

    // 피드 등록
    public void insert(FeedInsertRequest request) throws Exception{
        try {
            // 현재 로그인 한 멤버번호 가져오기
             Optional<Member> member =  memberRepository.findById(request.getMemberNo());
            // 피드 본문 insert
            Feed newFeed = feedRepository.save(converter.toFeedEntity(member.get(),request.getContent()));

            // 피드 이미지들 저장
            List<FeedImage> feedImageList =  converter.toFeedImageEntity(newFeed,request.getImages());
            feedImageService.insert(feedImageList);

            //피드 태그 insert
            List<Tag> tags = converter.toTagEntity(request.getTags());
            tagService.insert(tags,newFeed);

        }catch (Exception e){
            log.info(e.toString());
        }
    }

    // 피드 수정
    public void update(FeedInsertRequest request) {
        try{
            Optional<Feed> feed = feedRepository.findById(request.getFeedNo());
            Member member =  memberRepository.findById(feed.get().getMember().getMemberNo()).get();
            System.out.println("member, feed : "+feed.get().getFeedNo()+" "+member.getMemberNo());
            if(feed.isPresent()){
                Feed updateFeed = Feed.builder().feedNo(feed.get().getFeedNo()).member(member).content(request.getContent()).build();
                feedRepository.save(updateFeed);
            }else return;

            // 삭제된 사진 있다면
            if(request.getImageNo() !=null){
                System.out.println("delete images size : "+request.getImageNo().size());
                feedImageService.delete(request.getImageNo());
            }

            // 추가된 사진 있다면
            if(request.getImages() != null){
                System.out.println("update images : "+request.getImages().size());
                List<FeedImage> feedImageList =  converter.toFeedImageEntity(feed.get(),request.getImages());
                feedImageService.insert(feedImageList);
            }

            // 삭제된 태그 있다면
            if(request.getTagNo()!= null){
                System.out.println("delete tags : "+request.getTagNo().size());
                List<Tag> tagList = tagService.selectTags(request.getTagNo());
                for(Tag tag: tagList){
                    FeedTag delTag = tagService.selectFeedTag(feed.get(),tag);
                    tagService.delete(delTag);
                }
            }

            // 추가된 태그 있다면
            if(request.getTags()!= null){
                System.out.println("update tags : "+request.getTags().size());
                List<Tag> tags = converter.toTagEntity(request.getTags());
                tagService.insert(tags,feed.get());
            }
        }catch (Exception e){
            log.info(e.toString());
        }
    }
}
