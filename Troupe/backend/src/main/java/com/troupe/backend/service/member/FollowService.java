package com.troupe.backend.service.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.member.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private FollowRepository followRepository;

    @Autowired
    public void setFollowRepository(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /**
     * 팔로우 관계 등록
     */
    public void follow(Member star, Member fan) {
        followRepository.save(Follow.builder().starMember(star).fanMember(fan).build());
    }

    /**
     * 팔로우 관계 삭제
     */
    public void unfollow(Member star, Member fan)  {
        followRepository.delete(followRepository.findByStarMemberAndFanMember(star, fan).get());
    }

    /**
     * 현재 팔로우중인지 여부를 리턴
     */
    public boolean isFollowing(Member star, Member fan) {
        return followRepository.findByStarMemberAndFanMember(star, fan).isPresent();
    }

    /**
     * 팔로우 여부를 반대로 변경
     */
    public void toggleFollow(Member star, Member fan) {
        if (isFollowing(star, fan)) {
            unfollow(star, fan);
        } else {
            follow(star, fan);
        }
    }
}
