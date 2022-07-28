package com.troupe.backend.service.member;

import com.troupe.backend.exception.DuplicateMemberException;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.Avatar;
import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    private AvatarService avatarService;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setCharacterService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /**
     * 회원 등록
     */
    public Member saveMember(int memberNo, MemberForm memberForm) throws DuplicateMemberException {
        if (isDuplicateMember(memberForm)) {
            throw new DuplicateMemberException();
        }

        Avatar defaultAvatar = avatarService.getDefaultAvatar();
        Member member = null;

        return memberRepository.save(member);
    }

    /**
     * 회원 탈퇴
     */
    public void deleteMember(int memberNo) {
        Member foundMember = memberRepository.findById(memberNo).get(); // 실패 시 NoSuchElementException
        foundMember.setRemoved(true);
    }

    /**
     * 회원 기본정보 수정
     */
    public Member updateMember(int memberNo, MemberForm memberForm) throws DuplicateMemberException {
        Member foundMember = memberRepository.findById(memberNo).get(); // 실패 시 NoSuchElementException

        if (isDuplicateMember(memberNo, memberForm)) {
            throw new DuplicateMemberException();
        }

        foundMember.setPassword(memberForm.getPassword());
        foundMember.setNickname(memberForm.getNickname());
        foundMember.setDescription(memberForm.getDescription());
        foundMember.setMemberType(memberForm.getMemberType());
        foundMember.setProfileImageUrl(memberForm.getProfileImageUrl());

        return foundMember;
    }

    /**
     * 등록 시 이메일, 닉네임 중복 여부 리턴
     */
    public boolean isDuplicateMember(MemberForm memberForm) {
        return isDuplicateEmail(memberForm.getEmail()) || isDuplicateNickname(memberForm.getNickname());
    }

    /**
     * 수정 시 이메일, 닉네임 중복 여부 리턴
     */
    public boolean isDuplicateMember(int memberNo, MemberForm memberForm) {
        return isDuplicateEmail(memberNo, memberForm.getEmail()) || isDuplicateNickname(memberNo, memberForm.getNickname());
    }

    /**
     * 수정 시 이메일 중복 여부 리턴
     */
    public boolean isDuplicateEmail(int memberNo, String email) {
        Optional<Member> found = findByEmail(email);

        // 이메일이 DB에 존재하고, 그것이 다른 사람의 이메일이라면 중복
        return found.isPresent() && !found.get().getMemberNo().equals(memberNo);
    }

    /**
     * 등록 시 이메일 중복 여부 리턴
     */
    public boolean isDuplicateEmail(String email) {
        Optional<Member> found = findByEmail(email);

        // 이메일이 존재하면 중복
        return found.isPresent();
    }

    /**
     * 수정 시 닉네임 중복 여부 리턴
     */
    public boolean isDuplicateNickname(int memberNo, String nickname) {
        Optional<Member> found = findByNickname(nickname);

        // 다른 사람의 이메일이라면 중복
        return found.isPresent() && !found.get().getMemberNo().equals(memberNo);
    }

    /**
     * 등록 시 닉네임 중복 여부 리턴
     */
    public boolean isDuplicateNickname(String nickname) {
        Optional<Member> found = findByNickname(nickname);

        // 다른 사람의 이메일이라면 중복
        return found.isPresent();
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
