package com.troupe.backend.service;

import com.troupe.backend.Exception.DuplicateMemberException;
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

    /**
     * 회원 등록
     */
    public Member saveMember(Member member) throws DuplicateMemberException {
        if (isDuplicateMember(member)) {
            throw new DuplicateMemberException();
        }

        return memberRepository.save(member);
    }

    /**
     * 회원 탈퇴
     */
    public void deleteMember(int memberNo) {
        Member foundMember = memberRepository.findById(memberNo).get(); // 실패 시 NoSuchElementException
        foundMember.setRemoved(true);
    }

    /** 회원 기본정보 수정 */
    public Member modifyMember(Member member) throws DuplicateMemberException {
        Member foundMember = memberRepository.findById(member.getMemberNo()).get(); // 실패 시 NoSuchElementException

        if (isDuplicateMember(member)) {
            throw new DuplicateMemberException();
        }

        foundMember.setPassword(member.getPassword());
        foundMember.setNickname(member.getNickname());
        foundMember.setDescription(member.getDescription());
        foundMember.setMemberType(member.getMemberType());
        foundMember.setProfileImageUrl(member.getProfileImageUrl());

        return foundMember;
    }

    /**
     * 멤버의 이메일 또는 닉네임의 중복 여부 리턴
     */
    public boolean isDuplicateMember(Member member) {
        if (isDuplicateEmail(member.getMemberNo(), member.getEmail())) {
            return true;
        }
        if (isDuplicateNickname(member.getMemberNo(), member.getNickname())) {
            return true;
        }
        return false;
    }

    /**
     * 이메일 중복 여부 리턴
     */
    public boolean isDuplicateEmail(int memberNo, String email) {
        Optional<Member> found = findByEmail(email);

        // 다른 사람의 이메일이라면 중복
        if (found.isPresent() && !found.get().getMemberNo().equals(memberNo)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 닉네임 중복 여부 리턴
     */
    public boolean isDuplicateNickname(int memberNo, String nickname) {
        Optional<Member> found = findByNickname(nickname);

        // 다른 사람의 이메일이라면 중복
        if (found.isPresent() && !found.get().getMemberNo().equals(memberNo)) {
            return true;
        } else {
            return false;
        }
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
