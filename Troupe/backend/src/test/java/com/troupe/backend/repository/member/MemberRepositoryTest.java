package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveTest() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setMemberType(MemberType.AUDIENCE);
        member.setNickname("hello");

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getEmail()).isEqualTo("test@test.com");
        assertThat(savedMember.getMemberType()).isEqualTo(MemberType.AUDIENCE);
        assertThat(savedMember.getNickname()).isEqualTo("hello");
    }
}
