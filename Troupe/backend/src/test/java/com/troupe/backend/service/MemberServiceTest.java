package com.troupe.backend.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("멤버 등록/조회 테스트")
//    public void saveAndFindTest() {
//        Member A = memberService.saveMember(new Member("A", "A"));
//        Member B = new Member("B", "B");
//
//        assertThat(memberService.findById(A.getMemberNo()).get().getNickname()).isEqualTo("A");
//        assertThat(memberService.findById(B.getMemberNo()).isPresent()).isEqualTo(false);
//    }
//
//    @Test
//    @DisplayName("멤버 등록 시 중복 여부 체크 테스트")
//    public void saveDuplicateCheckTest() throws DuplicateMemberException {
//        // 기본 등록 테스트
//        Member A = memberService.saveMember(new Member("A", "A"));
//        assertThat(memberService.findById(A.getMemberNo()).get().getNickname()).isEqualTo("A");
//
//        // 중복이 아닌 경우 등록 성공 테스트
//        Member B = memberService.saveMember(new Member("B", "B"));
//        assertThat(memberService.findById(B.getMemberNo()).get().getNickname()).isEqualTo("B");
//
//        // 닉네임 중복 예외 테스트
//        assertThrows(DuplicateMemberException.class, () -> memberService.saveMember(new Member("A", "X")));
//
//        // 이메일 중복 예외 테스트
//        assertThrows(DuplicateMemberException.class, () -> memberService.saveMember(new Member("X", "A")));
//
//        // 닉네임, 이메일 모두 중복 예외 테스트
//        assertThrows(DuplicateMemberException.class, () -> memberService.saveMember(new Member("A", "A")));
//    }
//
//    @Test
//    @DisplayName("멤버 등록/수정 테스트")
//    public void saveAndUpdateTest() {
//
//    }
//
//    @Test
//    @DisplayName("멤버 수정 시 중복 여부 체크 테스트")
//    public void updateDuplicateCheckTest() throws DuplicateMemberException {
//
//    }
//
//    @Test
//    @DisplayName("멤버 등록/삭제 테스트")
//    public void saveAndDeleteTest() {
//
//    }
}
