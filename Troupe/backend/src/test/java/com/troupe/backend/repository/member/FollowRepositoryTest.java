package com.troupe.backend.repository.member;

import com.troupe.backend.domain.character.*;
import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FollowRepositoryTest {
    @Autowired FollowRepository followRepository;
    @Autowired MemberRepository memberRepository;

    /** 특정 멤버를 팔로우하는 팬 목록 조회 테스트 */
    @Test
    public void fansTest() {
        // 멤버 A, B, C 등록
        Member A = memberRepository.save(new Member("A"));
        Member B = memberRepository.save(new Member("B"));
        Member C = memberRepository.save(new Member("C"));

        // A <- B, A <- C, B <- C로 팔로우 등록
        Follow follow1 = followRepository.save(new Follow(A, B));
        Follow follow2 = followRepository.save(new Follow(A, C));
        Follow follow3 = followRepository.save(new Follow(B, C));

        // A를 팔로우중인 팬 리스트를 가져와서 set에 담기
        List<Follow> resultA = followRepository.findAllByStarMember(A);
        Set<String> fanNicknames = new HashSet<>();
        for (Follow f : resultA) {
            fanNicknames.add(f.getFanMember().getNickname());
        }

        // A의 팬은 B, C
        assertThat(fanNicknames.size()).isEqualTo(2);
        assertThat(fanNicknames.contains("B")).isEqualTo(true);
        assertThat(fanNicknames.contains("C")).isEqualTo(true);
        assertThat(fanNicknames.contains("A")).isEqualTo(false);

        // C를 팔로우중인 팬 리스트 조회
        List<Follow> resultC = followRepository.findAllByStarMember(C);

        // A의 팬은 없음
        assertThat(resultC.size()).isEqualTo(0);
    }

    /** 특정 멤버가 팔로우중인 스타 목록 조회 테스트 */
    @Test
    public void starsTest() {
        // 멤버 A, B, C 등록
        Member A = memberRepository.save(new Member("A"));
        Member B = memberRepository.save(new Member("B"));
        Member C = memberRepository.save(new Member("C"));

        // A <- B, A <- C, B <- C로 팔로우 등록
        Follow follow1 = followRepository.save(new Follow(A, B));
        Follow follow2 = followRepository.save(new Follow(A, C));
        Follow follow3 = followRepository.save(new Follow(B, C));

        // C가 팔로우중인 스타 리스트를 가져와서 set에 담기
        List<Follow> resultC = followRepository.findAllByFanMember(C);
        Set<String> starNicknames = new HashSet<>();
        for (Follow f : resultC) {
            starNicknames.add(f.getStarMember().getNickname());
        }

        // C의 스타는 A, B
        assertThat(starNicknames.size()).isEqualTo(2);
        assertThat(starNicknames.contains("A")).isEqualTo(true);
        assertThat(starNicknames.contains("B")).isEqualTo(true);
        assertThat(starNicknames.contains("C")).isEqualTo(false);

        // A의 스타 리스트 조회
        List<Follow> resultA = followRepository.findAllByFanMember(A);

        // A의 스타는 없음
        assertThat(resultA.size()).isEqualTo(0);
    }
}
