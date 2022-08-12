package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    /** 두 사람의 팔로우 관계 조회 */
    Optional<Follow> findByStarMemberAndFanMember(Member starMember, Member fanMember);

    /** 스타를 팔로우하는 리스트 조회 */
    List<Follow> findAllByStarMember(Member starMember);

    /** 팬이 팔로우하는 리스트 조회 */
    List<Follow> findAllByFanMember(Member fanMember);

    /** 스타의 팬 수 카운트 */
    Long countByStarMember(Member starMember);
}