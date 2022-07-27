package com.troupe.backend.repository.member;

import com.troupe.backend.domain.character.*;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    /** 샘플 멤버를 저장하는 함수 */
    private Member saveMemberSample() {
        CharacterClothes clothes = new CharacterClothes(1,"url");
        CharacterEye eyes = new CharacterEye(1,"url");
        CharacterHair hair = new CharacterHair(1,"url");
        CharacterMouth mouth = new CharacterMouth(1,"url");
        CharacterNose nose = new CharacterNose(1,"url");
        CharacterShape shape = new CharacterShape(1,"url");
        Member member  = new Member(3,"email","password","nickname","description", MemberType.PERFORMER,"url",false, clothes,eyes,hair,mouth,nose,shape);
        return memberRepository.save(member);
    }

    /** 저장, 조회 테스트 */
    @Test
    public void saveAndFindTest() {
        Member savedMember = saveMemberSample();

        // 저장한 회원 조회
        assertThat(savedMember.getMemberNo()).isEqualTo(3);
        assertThat(savedMember.getEmail()).isEqualTo("email");
        assertThat(savedMember.getPassword()).isEqualTo("password");
        assertThat(savedMember.getNickname()).isEqualTo("nickname");
        assertThat(savedMember.getDescription()).isEqualTo("description");
        assertThat(savedMember.getMemberType()).isEqualTo(MemberType.PERFORMER);
        assertThat(savedMember.isRemoved()).isEqualTo(false);
        assertThat(savedMember.getClothes().getClothesNo()).isEqualTo(1);
        assertThat(savedMember.getEye().getEyeNo()).isEqualTo(1);
        assertThat(savedMember.getHair().getHairNo()).isEqualTo(1);
        assertThat(savedMember.getMouth().getMouthNo()).isEqualTo(1);
        assertThat(savedMember.getNose().getNoseNo()).isEqualTo(1);
        assertThat(savedMember.getShape().getShapeNo()).isEqualTo(1);

        // 저장하지 않은 회원 조회
        assertThat(memberRepository.findById(987654321).isPresent()).isEqualTo(false);
    }

    /** 저장, 수정 테스트 */
    @Test
    public void saveAndUpdateTest() {
        // 회원 저장
        Member savedMember = saveMemberSample();

        // 저장된 회원 수정
        savedMember.setNickname("newnickname");
        savedMember.setPassword("newpassword");
        savedMember.setMemberType(MemberType.AUDIENCE);

        // 수정 내역 반영되었는지 조회
        Member foundMember = memberRepository.findById(3).get();
        assertThat(foundMember.getNickname()).isEqualTo("newnickname");
        assertThat(foundMember.getPassword()).isEqualTo("newpassword");
        assertThat(foundMember.getMemberType()).isEqualTo(MemberType.AUDIENCE);
    }

    /** 저장, 삭제 테스트 */
    @Test
    public void saveAndDeleteTest() {
        // 객체로 회원 삭제 테스트
        Member savedMember = saveMemberSample();
        memberRepository.delete(savedMember);
        assertThat(memberRepository.findById(3).isPresent()).isEqualTo(false);

        // 아이디로 회원 삭제 테스트
        Member savedMember2 = saveMemberSample();
        memberRepository.deleteById(savedMember2.getMemberNo());
        assertThat(memberRepository.findById(3).isPresent()).isEqualTo(false);

        // 없는 회원 삭제 예외 테스트
        assertThrows(EmptyResultDataAccessException.class, () -> memberRepository.deleteById(111));
    }
}
