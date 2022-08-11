package com.troupe.backend.service.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.feed.FeedLikeRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.service.member.LikabilityService;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FeedILikeService {

    private final FeedLikeRepository feedLikeRepository;

    private final MemberRepository memberRepository;

    private final FeedRepository feedRepository;

    private final LikabilityService likabilityService;

    public boolean insert(int memberNo, int feedNo) {
        Member member = memberRepository.findById(memberNo).get();
        Feed feed = feedRepository.findById(feedNo).get();
        Optional<FeedLike> feedLike = feedLikeRepository.findByMemberAndFeed(member, feed);
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        int starMemberNo = feed.getMember().getMemberNo();
        int fanMemberNo = memberNo;

        if (feedLike.isEmpty()) {
            // 최초 좋아요 시
            FeedLike feedLike1 = feedLikeRepository.save(FeedLike.builder().member(member).feed(feed).createdTime(now).build());

            // 호감도 갱신
            likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_FEED_LIKE);

            return feedLike1.isDeleted();
        } else {
            // 좋아요 스위칭
            boolean check = feedLike.get().isDeleted();
            feedLike.get().setDeleted(!check);
            feedLike.get().setCreatedTime(now);
            FeedLike feedLike1 = feedLikeRepository.save(feedLike.get());

            // 호감도 갱신
            if (check) {
                likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_FEED_LIKE);
            } else {
                likabilityService.updateExp(starMemberNo, fanMemberNo, -MyConstant.EXP_FEED_LIKE);
            }

            return feedLike1.isDeleted();
        }
    }

    public int countTotalLike(int feedNo) {
        Feed feed = feedRepository.findById(feedNo).get();
        int total = 0;
        if (feedLikeRepository.findByFeedAndIsDeletedFalse(feed) != null) {
            total = feedLikeRepository.findByFeedAndIsDeletedFalse(feed);
        }
        return total;
    }

    public boolean checkFeedLike(int memberNo, int feedNo) {
        Member member = memberRepository.findById(memberNo).get();
        Feed feed = feedRepository.findById(feedNo).get();
        Optional<FeedLike> check = feedLikeRepository.findByMemberAndFeedAndIsDeletedFalse(member, feed);
        if (check.isPresent()) return true;
        else return false;
    }
}
