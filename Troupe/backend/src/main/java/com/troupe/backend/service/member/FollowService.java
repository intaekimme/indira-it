package com.troupe.backend.service.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.exception.member.DuplicatedFollowException;
import com.troupe.backend.repository.member.FollowRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    private final LikabilityService likabilityService;

    /**
     * 팔로우 관계 등록
     */
    public Follow follow(int starMemberNo, int fanMemberNo) {
        if (isFollowing(starMemberNo, fanMemberNo)) {
            throw new DuplicatedFollowException();
        }

        // 팔로우 DB에 저장
        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        Follow savedFollow = followRepository.save(Follow.builder().starMember(starMember).fanMember(fanMember).build());

        // 호감도 갱신
        likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_FOLLOW);

        return savedFollow;
    }

    /**
     * 팔로우 관계 삭제
     */
    public void unfollow(int starMemberNo, int fanMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();

        // 팔로우 DB에서 삭제
        followRepository.delete(followRepository.findByStarMemberAndFanMember(starMember, fanMember).get());

        // 호감도 갱신
        likabilityService.updateExp(starMemberNo, fanMemberNo, -MyConstant.EXP_FOLLOW);
    }

    /**
     * 현재 팔로우중인지 여부를 리턴
     */
    public boolean isFollowing(int starMemberNo, int fanMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        return followRepository.findByStarMemberAndFanMember(starMember, fanMember).isPresent();
    }

    /**
     * 스타가 팔로우를 받은 팬들의 목록 조회
     */
    public List<Member> findAllFans(int starMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        List<Follow> follows = followRepository.findAllByStarMember(starMember);
        List<Member> ret = new ArrayList<>();
        for (Follow f : follows) {
            ret.add(f.getFanMember());
        }

        return ret;
    }

    /**
     * 팬이 팔로우를 누른 스타들의 목록 조회
     */
    public List<Member> findAllStars(int fanMemberNo) {
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        List<Follow> follows = followRepository.findAllByFanMember(fanMember);
        List<Member> ret = new ArrayList<>();
        for (Follow f : follows) {
            ret.add(f.getStarMember());
        }

        return ret;
    }

    /**
     * 팔로우 여부를 반대로 변경
     */
    public void toggleFollow(int starMemberNo, int fanMemberNo) {
        if (isFollowing(starMemberNo, fanMemberNo)) {
            unfollow(starMemberNo, fanMemberNo);
        } else {
            follow(starMemberNo, fanMemberNo);
        }

    }

    /**
     * 팬 수를 카운트
     */
    public long countFans (int starMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        return followRepository.countByStarMember(starMember);
    }
}
