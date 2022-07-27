package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    /** 스타를 팔로우하는 리스트 조회 */
    List<Follow> findAllByStarMember(Member starMember);
    /** 팬이 팔로우하는 리스트 조회 */
    List<Follow> findAllByFanMember(Member fanMember);
}