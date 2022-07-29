package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedTag;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.FeedConverter;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public FeedResponse select(int feedNo){
        Feed feed = feedRepository.findById(feedNo).get();
        List<FeedImage> feedImages = feedImageService.selectAll(feed);
        List<Tag> tags = tagService.selectAll(feed);
        FeedResponse feedResponse = converter.feedResponse(feed, feedImages,tags);
        return feedResponse;
    }

    public List<FeedResponse> selectAll(String change){
        List<FeedResponse> feedResponses = new ArrayList<>();
        if(change.equals("all")){
            List<Feed> totalList = feedRepository.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));
            for(Feed feed: totalList){
                feedResponses.add(select(feed.getFeedNo()));
            }
        }else if(change.equals("follow")){


        }else if(change.equals("save")){

        }
        return feedResponses;
    }

    public  List<FeedResponse> selectAllByMember(int memberNo){
        Member member = memberRepository.findById(memberNo).get();
        List<FeedResponse> feedResponses = new ArrayList<>();
        List<Feed> totalList = feedRepository.findAllByMemberOrderByCreatedTimeDesc(member);
        for(Feed feed: totalList){
            feedResponses.add(select(feed.getFeedNo()));
        }
        return feedResponses;
    }

    public  List<FeedResponse> selectAllBySearch(List<String> tagList){
        List<FeedResponse> feedResponses = new ArrayList<>();
        List<Tag> tags = converter.toTagEntity(tagList);
        List<FeedTag> feedTags = tagService.selectAllBySearch(tags);
        for(FeedTag feedTag:feedTags){
            feedResponses.add(select(feedTag.getFeed().getFeedNo()));
        }
        return feedResponses;
    }
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
//            System.out.println("member, feed : "+feed.get().getFeedNo()+" "+member.getMemberNo());
            if(feed.isPresent()){
                Feed updateFeed = Feed.builder().feedNo(feed.get().getFeedNo()).member(member).content(request.getContent()).build();
                feedRepository.save(updateFeed);
            }else return;

            // 삭제된 사진 있다면
            if(request.getImageNo() !=null){
//                System.out.println("delete images size : "+request.getImageNo().size());
                feedImageService.delete(request.getImageNo());
            }

            // 추가된 사진 있다면
            if(request.getImages() != null){
//                System.out.println("update images : "+request.getImages().size());
                List<FeedImage> feedImageList =  converter.toFeedImageEntity(feed.get(),request.getImages());
                feedImageService.insert(feedImageList);
            }

            // 태그 수정
            if(request.getTags()!= null){
//                System.out.println("update tags : "+request.getTags().size());
                // number 없는 태그들일수있따
                List<Tag> tags = converter.toTagEntity(request.getTags());
                tagService.deleteAll(feed.get());
                tagService.insert(tags,feed.get());
            }

        }catch (Exception e){
            log.info(e.toString());
        }
    }

    public void  delete(int feedNo){
        try{
            Feed delFeed = feedRepository.findById(feedNo).get();
            delFeed.setRemoved(true);
            feedRepository.save(delFeed);
        }catch (Exception e){
            log.info(e.toString());
        }
    }
}
