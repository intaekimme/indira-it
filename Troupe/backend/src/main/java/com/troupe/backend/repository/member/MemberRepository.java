package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);
}