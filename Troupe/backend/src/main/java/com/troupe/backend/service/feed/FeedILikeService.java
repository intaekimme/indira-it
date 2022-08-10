package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.feed.FeedImageRepository;
import com.troupe.backend.repository.feed.FeedLikeRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedILikeService {

    @Autowired
    FeedLikeRepository feedLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FeedRepository feedRepository;

     public boolean insert(int memberNo, int feedNo){
         Member member = memberRepository.findById(memberNo).get();
         Feed feed = feedRepository.findById(feedNo).get();
         Optional<FeedLike> feedLike = feedLikeRepository.findByMemberAndFeed(member,feed);
         LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
         Date now = java.sql.Timestamp.valueOf(localDateTime);
         if(feedLike.isEmpty()){
             // 최초 좋아요 시
             FeedLike feedLike1 = feedLikeRepository.save(FeedLike.builder().member(member).feed(feed).createdTime(now).build());
             return feedLike1.isDeleted();
         }else{
             // 좋아요 스위칭
             boolean check = feedLike.get().isDeleted();
             feedLike.get().setDeleted(!check);
             feedLike.get().setCreatedTime(now);
             FeedLike feedLike1 = feedLikeRepository.save(feedLike.get());
             return feedLike1.isDeleted();
         }
     }

     public int countTotalLike (int feedNo){
         Feed feed = feedRepository.findById(feedNo).get();
         int total = 0;
         if(feedLikeRepository.findByFeedAndIsDeletedFalse(feed) != null){
             total=feedLikeRepository.findByFeedAndIsDeletedFalse(feed);
         }
         return total;
     }

     public boolean checkFeedLike(int memberNo,int feedNo){
         Member member = memberRepository.findById(memberNo).get();
         Feed feed = feedRepository.findById(feedNo).get();
         Optional<FeedLike> check = feedLikeRepository.findByMemberAndFeedAndIsDeletedFalse(member,feed);
         if(check.isPresent()) return true;
         else return false;
     }
}
