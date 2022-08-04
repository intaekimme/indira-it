package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.*;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.FeedConverter;
import com.troupe.backend.dto.feed.FeedForm;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.feed.FeedTagRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.service.member.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;

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
    FeedSaveService feedSaveService;

    @Autowired
    FollowService followService;

    @Autowired
    FeedConverter feedConverter;

    @Autowired
    FeedTagRepository feedTagRepository;

    public FeedResponse select(int feedNo) {
        Feed feed = feedRepository.findById(feedNo).get();
        List<FeedImage> feedImages = feedImageService.selectAll(feed);
        List<Tag> tags = tagService.selectAll(feed);
        FeedResponse feedResponse = feedConverter.feedResponse(feed, feedImages, tags);
        return feedResponse;
    }

    public List<FeedResponse> selectAll(String change, int memberNo, Pageable pageable) {
        List<FeedResponse> feedResponses = new ArrayList<>();
        if (change.equals("all")) {
            Slice<Feed> feedList = feedRepository.findAllByIsRemovedOrderByCreatedTimeDesc(false, pageable).get();
            for (Feed feed : feedList) {
                feedResponses.add(select(feed.getFeedNo()));
            }
//            Slice<FeedResponse> feedResponses = feedList.map(m->
//                    select(m.getFeedNo())
//                    );
//            return feedResponses;
        } else if (change.equals("follow")) {
            List<Member> followings = followService.findAllStars(memberNo);
            Slice<Feed> feedList = feedRepository.findAllByIsRemovedAndMemberInOrderByCreatedTimeDesc(false, followings, pageable);
            for (Feed feed : feedList) {
                feedResponses.add(select(feed.getFeedNo()));
            }
        } else if (change.equals("save")) {
            Slice<FeedSave> saveLIst = feedSaveService.selectAllByMemberWithPaging(memberRepository.findById(memberNo).get(), pageable);
            for (FeedSave save : saveLIst) {
                feedResponses.add(select(save.getFeed().getFeedNo()));
            }
        }
        return feedResponses;
    }

    public List<FeedResponse> selectAllByMember(int memberNo, Pageable pageable) {
        Member member = memberRepository.findById(memberNo).get();
        List<FeedResponse> feedResponses = new ArrayList<>();
        Slice<Feed> totalList = feedRepository.findAllByMemberAndIsRemovedOrderByCreatedTimeDesc(member, false, pageable);
        for (Feed feed : totalList) {
            feedResponses.add(select(feed.getFeedNo()));
        }
//        Slice<FeedResponse> feedResponses =  totalList.map(m->
//                select(m.getFeedNo())
//        );
        return feedResponses;
    }

    public List<FeedResponse> selectAllBySearch(List<String> tags) {
        List<FeedResponse> feedResponses = new ArrayList<>();
        try {
            List<FeedTag> feedTags = tagService.selectAllBySearch(tags);
            for (FeedTag feedTag : feedTags) {
                feedResponses.add(select(feedTag.getFeed().getFeedNo()));
            }
            return feedResponses;
        } catch (Exception e) {
            log.info(e.toString());
        }
        return feedResponses;
    }

    // 피드 등록
    public void insert(FeedForm request) throws Exception {
        try {
            // 현재 로그인 한 멤버번호 가져오기
            Optional<Member> member = memberRepository.findById(request.getMemberNo());
            // 피드 본문 insert
            Feed newFeed = feedRepository.save(feedConverter.toFeedEntity(member.get(), request.getContent()));

            // 피드 이미지들 저장(없으면 null 뜬다)
            List<FeedImage> feedImageList = feedConverter.toFeedImageEntity(newFeed, request.getImages());
            feedImageService.insert(feedImageList);

            //피드 태그 insert(없으면 null뜬다)
            List<Tag> tags = feedConverter.toTagEntity(request.getTags());
            tagService.insert(tags, newFeed);

        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    // 피드 수정
    public void update(FeedForm request) {
        try {
            Optional<Feed> feed = feedRepository.findById(request.getFeedNo());
            Member member = memberRepository.findById(feed.get().getMember().getMemberNo()).get();
//            System.out.println("member, feed : "+feed.get().getFeedNo()+" "+member.getMemberNo());
            if (feed.isPresent()) {
                Feed updateFeed = Feed.builder().feedNo(feed.get().getFeedNo()).member(member).content(request.getContent()).createdTime(feed.get().getCreatedTime()).build();
                feedRepository.save(updateFeed);
            } else return;

            // 삭제된 사진 있다면
            if (request.getImageNo() != null) {
//                System.out.println("delete images size : "+request.getImageNo().size());
                feedImageService.delete(request.getImageNo());
            }

            // 추가된 사진 있다면
            if (request.getImages() != null) {
//                System.out.println("update images : "+request.getImages().size());
                List<FeedImage> feedImageList = feedConverter.toFeedImageEntity(feed.get(), request.getImages());
                feedImageService.insert(feedImageList);
            }

            // 태그 수정
            List<Tag> tags = feedConverter.toTagEntity(request.getTags());
            tagService.deleteAll(feed.get());
            if (request.getTags() != null) {
                tagService.insert(tags, feed.get());
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    public void delete(int feedNo) {
        try {
            Feed delFeed = feedRepository.findById(feedNo).get();
            delFeed.setRemoved(true);
            feedRepository.save(delFeed);
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    /**
     * 관심 태그 top 4를 리턴
     */
    public List<Tag> getTop4InterestTagList(int memberNo) {
        Member member = memberRepository.findById(memberNo).get();

        // 저장한 모든 피드 목록
        List<FeedSave> feedSaveList = feedSaveService.findAllByMember(member);

        // 해시태그 개수 카운팅
        Map<Tag, Integer> tagCount = new HashMap<>();
        for (FeedSave feedSave : feedSaveList) {
            List<FeedTag> feedTagList = feedTagRepository.findAllByFeed(feedSave.getFeed());
            for (FeedTag feedTag : feedTagList) {
                Tag tag = feedTag.getTag();
                tagCount.put(tag, 1 + tagCount.getOrDefault(tag, 0));
            }
        }

        // 맵을 리스트화해서 value (카운트 값) 으로 내림차순 정렬
        List<Map.Entry<Tag, Integer>> entryList = new LinkedList<>(tagCount.entrySet());
        entryList.sort(Map.Entry.<Tag, Integer>comparingByValue().reversed());

        // 탑4 가져오기
        List<Tag> ret = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i < entryList.size()) {
                ret.add(entryList.get(i).getKey());
            }
        }

        return ret;
    }
}
