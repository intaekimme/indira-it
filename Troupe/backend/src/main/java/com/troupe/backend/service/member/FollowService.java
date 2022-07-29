package com.troupe.backend.service.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.exception.member.DuplicatedFollowException;
import com.troupe.backend.repository.member.FollowRepository;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FollowService {
    private FollowRepository followRepository;

    private MemberRepository memberRepository;

    @Autowired
    public void setFollowRepository(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /**
     * 팔로우 관계 등록
     */
    public Follow follow(int starMemberNo, int fanMemberNo) {
        if (isFollowing(starMemberNo, fanMemberNo)) {
            throw new DuplicatedFollowException();
        }

        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        return followRepository.save(Follow.builder().starMember(starMember).fanMember(fanMember).build());
    }

    /**
     * 팔로우 관계 삭제
     */
    public void unfollow(int starMemberNo, int fanMemberNo) {
        // 여기도 짜야 함

        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        followRepository.delete(followRepository.findByStarMemberAndFanMember(starMember, fanMember).get());
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
    public List<Follow> findFans(int starMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        return followRepository.findAllByStarMember(starMember);
    }

    /**
     * 팬이 팔로우를 누른 스타들의 목록 조회
     */
    public List<Follow> findStars(int fanMemberNo) {
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        return followRepository.findAllByFanMember(fanMember);
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
}
