package com.troupe.backend.repository.likability;

import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikabilityRepositoryTest {
    @Autowired
    LikabilityRepository likabilityRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveAndFindTest () {
        Member star1 = memberRepository.save(new Member("star1"));
        Member star2 = memberRepository.save(new Member("star2"));
        Member fan1 = memberRepository.save(new Member("fan1"));
        Member fan2 = memberRepository.save(new Member("fan2"));
        Member fan3 = memberRepository.save(new Member("fan3"));

        // fan1 -> star1, fan2 -> star1, fan1 -> star2의 호감도 저장
        likabilityRepository.save(Likability.builder().starMember(star1).fanMember(fan1).exp(100).build());
        likabilityRepository.save(Likability.builder().starMember(star1).fanMember(fan2).exp(50).build());
        likabilityRepository.save(Likability.builder().starMember(star2).fanMember(fan1).exp(10).build());

        // 호감도 조회
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star1, fan1).get().getExp()).isEqualTo(100);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star1, fan2).get().getExp()).isEqualTo(50);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star1, fan3).isPresent()).isEqualTo(false);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star2, fan1).get().getExp()).isEqualTo(10);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star2, fan2).isPresent()).isEqualTo(false);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star2, fan3).isPresent()).isEqualTo(false);

        // fan1의 모든 스타에 대한 호감도 목록 조회
        List<Likability> likesOfFan1 = likabilityRepository.findAllByFanMember(fan1);
        assertThat(likesOfFan1.size()).isEqualTo(2);
        for (Likability like : likesOfFan1) {
            if (like.getStarMember().equals(star1)) {
                assertThat(like.getExp()).isEqualTo(100);
            }
            else if (like.getStarMember().equals(star2)) {
                assertThat(like.getExp()).isEqualTo(10);
            }
        }

        // fan2의 모든 스타에 대한 호감도 목록 조회
        List<Likability> likesOfFan2 = likabilityRepository.findAllByFanMember(fan2);
        assertThat(likesOfFan2.size()).isEqualTo(1);

        // fan3의 모든 스타에 대한 호감도 목록 조회
        List<Likability> likesOfFan3 = likabilityRepository.findAllByFanMember(fan3);
        assertThat(likesOfFan3.size()).isEqualTo(0);

        // star1의 모든 팬에 대한 호감도 목록 조회
        List<Likability> likesOfStar1 = likabilityRepository.findAllByStarMember(star1);
        assertThat(likesOfStar1.size()).isEqualTo(2);
        for (Likability like : likesOfFan1) {
            if (like.getStarMember().equals(fan1)) {
                assertThat(like.getExp()).isEqualTo(100);
            }
            else if (like.getStarMember().equals(fan2)) {
                assertThat(like.getExp()).isEqualTo(50);
            }
        }

        // star2의 모든 팬에 대한 호감도 목록 조회
        List<Likability> likesOfStar2 = likabilityRepository.findAllByStarMember(star2);
        assertThat(likesOfStar2.size()).isEqualTo(1);
    }

    @Test
    public void saveAndUpdateTest() {
        Member star = memberRepository.save(new Member("star"));
        Member fan = memberRepository.save(new Member("fan"));

        Likability like = likabilityRepository.save(Likability.builder().starMember(star).fanMember(fan).exp(0).build());
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star, fan).get().getExp()).isEqualTo(0);

        // 경험치 5 증가 테스트
        like.setExp(like.getExp() + 5);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star, fan).get().getExp()).isEqualTo(5);

        // 경험치 3 감소 테스트
        like.setExp(like.getExp() - 3);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star, fan).get().getExp()).isEqualTo(2);
    }

    @Test
    public void saveAndDeleteTest() {
        Member star = memberRepository.save(new Member("star"));
        Member fan = memberRepository.save(new Member("fan"));

        // 등록 후 조회시 결과 존재
        Likability like = likabilityRepository.save(Likability.builder().starMember(star).fanMember(fan).exp(0).build());
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star, fan).isPresent()).isEqualTo(true);

        // 삭제 후 조회시 결과 없음
        likabilityRepository.delete(like);
        assertThat(likabilityRepository.findByStarMemberAndFanMember(star, fan).isPresent()).isEqualTo(false);
    }

}
