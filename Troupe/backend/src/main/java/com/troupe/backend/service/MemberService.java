package com.troupe.backend.service;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** 회원 등록 */
    public void registerMember(Member member) {

    }

    /** 회원 탈퇴 */
    public void withdrawMember(Member member) {

    }

    public Optional<Member> findById(int memberNo) {
        return memberRepository.findById(memberNo);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
}
