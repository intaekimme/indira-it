package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Follow;
import com.troupe.backend.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberRepository memberRepository;

//    @Test
//    @DisplayName("팔로우 등록/삭제 테스트")
//    public void saveAndFindTest() {
//        // 멤버 A, B 등록
//        Member A = memberRepository.save(new Member("A", "A"));
//        Member B = memberRepository.save(new Member("B", "B"));
//
//        // A <- B 팔로우 등록 후 조회 테스트
//        Follow follow = followRepository.save(Follow.builder().starMember(A).fanMember(B).build());
//        assertThat(followRepository.findByStarMemberAndFanMember(A, B).isPresent()).isEqualTo(true);
//
//        // 삭제 후 조회 테스트
//        followRepository.delete(follow);
//        assertThat(followRepository.findByStarMemberAndFanMember(A, B).isPresent()).isEqualTo(false);
//    }
//
//    @Test
//    @DisplayName("팔로우 등록/조회 테스트")
//    public void saveAndDeleteTest() {
//        // 멤버 A, B 등록
//        Member A = memberRepository.save(new Member("A", "A"));
//        Member B = memberRepository.save(new Member("B", "B"));
//
//        // A <- B 팔로우 등록
//        Follow follow = followRepository.save(Follow.builder().starMember(A).fanMember(B).build());
//
//        // 관계 조회 테스트
//        assertThat(followRepository.findByStarMemberAndFanMember(A, B).isPresent()).isEqualTo(true);
//        // 삭제 테스트
//        assertThat(followRepository.findByStarMemberAndFanMember(A, A).isPresent()).isEqualTo(false);
//        assertThat(followRepository.findByStarMemberAndFanMember(B, A).isPresent()).isEqualTo(false);
//    }
//
//    @Test
//    @DisplayName("특정 멤버를 팔로우하는 팬 목록 조회 테스트")
//    public void fansTest() {
//        // 멤버 A, B, C 등록
//        Member A = memberRepository.save(new Member("A", "A"));
//        Member B = memberRepository.save(new Member("B", "B"));
//        Member C = memberRepository.save(new Member("C", "C"));
//
//        // A <- B, A <- C, B <- C로 팔로우 등록
//        Follow follow1 = followRepository.save(Follow.builder().starMember(A).fanMember(B).build());
//        Follow follow2 = followRepository.save(Follow.builder().starMember(A).fanMember(C).build());
//        Follow follow3 = followRepository.save(Follow.builder().starMember(B).fanMember(C).build());
//
//        // A를 팔로우중인 팬 리스트를 가져와서 set에 담기
//        List<Follow> resultA = followRepository.findAllByStarMember(A);
//        Set<String> fanNicknames = new HashSet<>();
//        for (Follow f : resultA) {
//            fanNicknames.add(f.getFanMember().getNickname());
//        }
//
//        // A의 팬은 B, C
//        assertThat(fanNicknames.size()).isEqualTo(2);
//        assertThat(fanNicknames.contains("B")).isEqualTo(true);
//        assertThat(fanNicknames.contains("C")).isEqualTo(true);
//        assertThat(fanNicknames.contains("A")).isEqualTo(false);
//
//        // C를 팔로우중인 팬 리스트 조회
//        List<Follow> resultC = followRepository.findAllByStarMember(C);
//
//        // A의 팬은 없음
//        assertThat(resultC.size()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName("특정 멤버가 팔로우중인 스타 목록 조회 테스트")
//    public void starsTest() {
//        // 멤버 A, B, C 등록
//        Member A = memberRepository.save(new Member("A", "A"));
//        Member B = memberRepository.save(new Member("B", "B"));
//        Member C = memberRepository.save(new Member("C", "C"));
//
//        // A <- B, A <- C, B <- C로 팔로우 등록
//        Follow follow1 = followRepository.save(Follow.builder().starMember(A).fanMember(B).build());
//        Follow follow2 = followRepository.save(Follow.builder().starMember(A).fanMember(C).build());
//        Follow follow3 = followRepository.save(Follow.builder().starMember(B).fanMember(C).build());
//
//        // C가 팔로우중인 스타 리스트를 가져와서 set에 담기
//        List<Follow> resultC = followRepository.findAllByFanMember(C);
//        Set<String> starNicknames = new HashSet<>();
//        for (Follow f : resultC) {
//            starNicknames.add(f.getStarMember().getNickname());
//        }
//
//        // C의 스타는 A, B
//        assertThat(starNicknames.size()).isEqualTo(2);
//        assertThat(starNicknames.contains("A")).isEqualTo(true);
//        assertThat(starNicknames.contains("B")).isEqualTo(true);
//        assertThat(starNicknames.contains("C")).isEqualTo(false);
//
//        // A의 스타 리스트 조회
//        List<Follow> resultA = followRepository.findAllByFanMember(A);
//
//        // A의 스타는 없음
//        assertThat(resultA.size()).isEqualTo(0);
//
//    }
}
