package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GuestbookRepositoryTest {
    @Autowired
    GuestbookRepository guestbookRepository;
    @Autowired
    MemberRepository memberRepository;

    /** 저장, 조회 테스트 */
    @Test
    public void saveAndFindTest() {
        Member host = memberRepository.save(new Member("host"));
        Member visitor = memberRepository.save(new Member("visitor"));
        Member visitor2 = new Member("visitor2");

        Guestbook guestbook = guestbookRepository.save(Guestbook.builder().hostMember(host).visitorMember(visitor).content("hello").build());
        Assertions.assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor).get()).isEqualTo(guestbook);
        Assertions.assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor2).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        Member host = memberRepository.save(new Member("host"));
        Member visitor = memberRepository.save(new Member("visitor"));

        Guestbook guestbook = guestbookRepository.save(Guestbook.builder().hostMember(host).visitorMember(visitor).content("hello").build());
        assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor).get().getContent()).isEqualTo("hello");

        guestbook.setContent("goodbye");
        assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor).get().getContent()).isEqualTo("goodbye");
    }

    @Test
    public void saveAndDeleteTest() {
        Member host = memberRepository.save(new Member("host"));
        Member visitor = memberRepository.save(new Member("visitor"));

        Guestbook guestbook = guestbookRepository.save(Guestbook.builder().hostMember(host).visitorMember(visitor).content("hello").build());
        assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor).isPresent()).isEqualTo(true);

        guestbookRepository.delete(guestbook);
        assertThat(guestbookRepository.findByHostMemberAndVisitorMember(host, visitor).isPresent()).isEqualTo(false);
    }
}
