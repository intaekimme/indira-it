package com.troupe.backend.service.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.Avatar;
import com.troupe.backend.dto.avatar.form.AvatarForm;
import com.troupe.backend.dto.member.LoginForm;
import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.exception.member.DuplicatedMemberException;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AvatarService avatarService;
    private final S3FileUploadService s3FileUploadService;

    /**
     * 회원 등록
     */
    public Member saveMember(MemberForm memberForm) throws IOException {
        System.out.println(memberForm.toString());

        // 중복 체크
        if (isDuplicateMember(memberForm)) {
            throw new DuplicatedMemberException();
        }

        // 기본 아바타 조회
        Avatar defaultAvatar = avatarService.findDefaultAvatar();

        // 프로필 사진 저장
        String imageUrl = "";
        if (memberForm.getProfileImage() != null && !memberForm.getProfileImage().isEmpty()) {
            imageUrl = s3FileUploadService.upload(memberForm.getProfileImage(), "profile");
        }

        // 멤버 생성
        Member member = Member.builder()
                .email(memberForm.getEmail())
                .password(memberForm.getPassword())
                .nickname(memberForm.getNickname())
                .description(memberForm.getDescription())
                .memberType(memberForm.getMemberType())
                .profileImageUrl(imageUrl)
                .isRemoved(false)
                .clothes(defaultAvatar.getAvatarClothes())
                .eye(defaultAvatar.getAvatarEye())
                .hair(defaultAvatar.getAvatarHair())
                .mouth(defaultAvatar.getAvatarMouth())
                .nose(defaultAvatar.getAvatarNose())
                .shape(defaultAvatar.getAvatarShape())
                .build();

        // 저장
        return memberRepository.save(member);
    }

    public Member login(LoginForm loginForm) {
        Member foundMember = memberRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).get();
        return foundMember;
    }

    /**
     * 회원 탈퇴
     */
    public Member deleteMember(int memberNo) {
        Member foundMember = memberRepository.findById(memberNo).get(); // 실패 시 NoSuchElementException
        foundMember.setRemoved(true);
        return foundMember;
    }

    /**
     * 회원 기본정보 수정
     */
    public Member updateMember(int memberNo, MemberForm memberForm) throws IOException {
        Member foundMember = memberRepository.findById(memberNo).get(); // 실패 시 NoSuchElementException

        if (isDuplicateMember(foundMember.getMemberNo(), memberForm)) {
            throw new DuplicatedMemberException();
        }

        String imageUrl = "";
        if (memberForm.getProfileImage() != null && !memberForm.getProfileImage().isEmpty()) {
            imageUrl = s3FileUploadService.upload(memberForm.getProfileImage(), "profile");
        }

        foundMember.setEmail(memberForm.getEmail());
        foundMember.setPassword(memberForm.getPassword());
        foundMember.setNickname(memberForm.getNickname());
        foundMember.setDescription(memberForm.getDescription());
        foundMember.setMemberType(memberForm.getMemberType());
        foundMember.setProfileImageUrl(imageUrl);

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

    /**
     * 멤버의 아바타를 수정
     */
    public Avatar updateMemberAvatar(int memberNo, AvatarForm avatarForm) {
        Member foundMember = memberRepository.findById(memberNo).get();
        Avatar avatar = avatarService.findAvatar(avatarForm);

        foundMember.setClothes(avatar.getAvatarClothes());
        foundMember.setEye(avatar.getAvatarEye());
        foundMember.setHair(avatar.getAvatarHair());
        foundMember.setMouth(avatar.getAvatarMouth());
        foundMember.setNose(avatar.getAvatarNose());
        foundMember.setShape(avatar.getAvatarShape());

        return avatar;
    }

    /**
     * 아이디로 멤버 찾기
     */
    public Optional<Member> findById(int memberNo) {
        return memberRepository.findById(memberNo);
    }

    /**
     * 이메일로 멤버 찾기
     */
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /**
     * 닉네임으로 멤버 찾기
     */
    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    /**
     * 스프링 시큐리티에서 관리하는 UserDetailsService의 메소드 오버라이딩 구현
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(Integer.parseInt(username))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
