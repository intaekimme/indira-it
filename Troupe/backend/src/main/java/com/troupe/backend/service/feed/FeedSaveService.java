package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.feed.FeedSave;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.feed.FeedLikeRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.feed.FeedSaveRepository;
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
public class FeedSaveService {

    @Autowired
    FeedSaveRepository feedSaveRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FeedRepository feedRepository;

     public boolean insert(int memberNo, int feedNo){
         Member member = memberRepository.findById(memberNo).get();
         Feed feed = feedRepository.findById(feedNo).get();
         Optional<FeedSave> feedSave = feedSaveRepository.findByMemberAndFeed(member,feed);
         if(feedSave.isEmpty()){
             // 최초 좋아요 시
             FeedSave feedSave1 = feedSaveRepository.save(FeedSave.builder().member(member).feed(feed).build());
             return feedSave1.isDeleted();
         }else{
             // 좋아요 스위칭
             boolean check = feedSave.get().isDeleted();
             feedSave.get().setDeleted(!check);
             FeedSave feedSave1 = feedSaveRepository.save(feedSave.get());
             return feedSave1.isDeleted();
         }
     }

     public List<FeedSave> selectAllByMember(Member member){
         Optional<List<FeedSave>> saveList = feedSaveRepository.findAllByMemberAndIsDeletedOrderByCreatedTime(member,false);
        if(saveList.isPresent()){
            return saveList.get();
        }else return null;
     }
}
