package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import com.troupe.backend.repository.character.CharacterClothesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveTest() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("password");
        member.setMemberType(MemberType.AUDIENCE);
        member.setNickname("hello");

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getEmail()).isEqualTo("test@test.com");
        assertThat(savedMember.getPassword()).isEqualTo("password");
        assertThat(savedMember.getMemberType()).isEqualTo(MemberType.AUDIENCE);
        assertThat(savedMember.getNickname()).isEqualTo("hello");
    }
}
