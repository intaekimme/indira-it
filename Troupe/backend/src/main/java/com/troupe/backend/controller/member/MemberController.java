package com.troupe.backend.controller.member;

import com.troupe.backend.domain.email.EmailToken;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.form.AvatarForm;
import com.troupe.backend.dto.avatar.response.AvatarResponse;
import com.troupe.backend.dto.member.form.*;
import com.troupe.backend.dto.member.response.MemberInfoResponse;
import com.troupe.backend.dto.security.TokenResponse;
import com.troupe.backend.service.email.EmailTokenService;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.service.security.JwtTokenProvider;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@Api("회원정보 REST API")
@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    private final EmailTokenService emailTokenService;

    @Operation(summary = "회원가입", description = "파라미터 : 회원가입 폼")
    @PostMapping("/signup")
    private ResponseEntity register(@ModelAttribute @Valid MemberRegisterForm memberRegisterForm) throws IOException {
        System.out.println(memberRegisterForm.toString());

        memberService.saveMember(memberRegisterForm);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인", description = "파라미터 : email, password")
    @PostMapping("/login")
    private ResponseEntity login(@RequestBody LoginForm loginForm) {
        Member member = memberService.login(loginForm);

        TokenResponse tokenResponse = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

        return new ResponseEntity(tokenResponse, HttpStatus.OK);
    }

    @Operation(summary = "회원 기본정보 수정", description = "파라미터 : accessToken (리퀘스트헤더), 멤버수정폼 (모델어트리뷰트) ")
    @PatchMapping("/myinfo")
    private ResponseEntity updateMember(Principal principal, @ModelAttribute @Valid MemberModifyForm memberModifyForm) throws IOException {
        int memberNo = Integer.parseInt(principal.getName());
        System.out.println("memberNo = " + memberNo);
        System.out.println("memberForm = " + memberModifyForm.toString());

        memberService.updateMember(memberNo, memberModifyForm);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴", description = "파라미터 : accessToken (리퀘스트헤더) ")
    @PatchMapping("/withdrawal")
    private ResponseEntity deleteMember(Principal principal) {
        int memberNo = Integer.parseInt(principal.getName());
        memberService.deleteMember(memberNo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "비밀번호 재설정을 위한 이메일 전송", description = "파라미터 : email (리퀘스트바디)")
    @PostMapping("request-password")
    private ResponseEntity sendPasswordResetEmail(@RequestBody EmailForm emailForm) {
        emailTokenService.sendResetPasswordEmail(emailForm.getEmail());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MESSAGE, emailForm.getEmail() + " 주소로 비밀번호 재설정 이메일이 전송되었습니다.\n");
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 재설정", description = "파라미터 : token (리퀘스트파람), password (리퀘스트바디)")
    @PostMapping("/reset-password")
    private ResponseEntity resetPassword(@RequestParam String token, @RequestBody PasswordForm passwordForm) {
        String password = passwordForm.getPassword();

        Optional<EmailToken> found = emailTokenService.findValidEmailToken(token);
        if (found.isPresent()) {
            EmailToken emailToken = found.get();
            emailToken.setTokenToUsed();
            memberService.resetPassword(emailToken.getEmail(), password);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(MyConstant.MESSAGE, "비밀번호 재설정 성공\n");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(MyConstant.MESSAGE, "비밀번호 재설정 실패 : 유효하지 않은 토큰\n");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "멤버의 정보 조회", description = "파라미터 : memberNo (패스배리어블) ")
    @GetMapping("/{memberNo}")
    private ResponseEntity getMemberInfo(@PathVariable int memberNo) {
        Member foundMember = memberService.findById(memberNo).get();
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(foundMember);
        return new ResponseEntity(memberInfoResponse, HttpStatus.OK);
    }

    @Operation(summary = "로그인중인 회원이 자신의 정보 조회", description = "파라미터 : accessToken (리퀘스트헤더) ")
    @GetMapping("/myinfo")
    private ResponseEntity getMemberInfo(Principal principal) {
        int memberNo = Integer.parseInt(principal.getName());
        Member foundMember = memberService.findById(memberNo).get();
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(foundMember);
        return new ResponseEntity(memberInfoResponse, HttpStatus.OK);
    }

    @Operation(summary = "이메일 중복 체크", description = "파라미터 : email")
    @PostMapping("/signup/email")
    private ResponseEntity checkDuplicateEmail(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        Map<String, Object> resultMap = new HashMap<>();

        if (memberService.isDuplicateEmail(email)) {
            resultMap.put(MyConstant.MESSAGE, "이미 사용 중인 이메일입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.CONFLICT);
        } else {
            resultMap.put(MyConstant.MESSAGE, "사용 가능한 이메일입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }
    }

    @Operation(summary = "닉네임 중복 체크", description = "파라미터 : nickname")
    @PostMapping("/signup/nickname")
    private ResponseEntity checkDuplicateNickname(@RequestBody Map<String, Object> requestBody) {
        String nickname = (String) requestBody.get(MyConstant.NICKNAME);

        Map<String, Object> resultMap = new HashMap<>();
        if (memberService.isDuplicateNickname(nickname)) {
            resultMap.put(MyConstant.MESSAGE, "이미 사용 중인 닉네임입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.CONFLICT);
        } else {
            resultMap.put(MyConstant.MESSAGE, "사용 가능한 닉네임입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }
    }

    @Operation(summary = "멤버의 아바타 조회", description = "파라미터 : memberNo (패스배리어블)")
    @GetMapping("/{memberNo}/avatar")
    private ResponseEntity getAvatar(@PathVariable int memberNo) {
        Member foundMember = memberService.findById(memberNo).get();
        AvatarResponse avatarResponse = new AvatarResponse(foundMember);
        return new ResponseEntity(avatarResponse, HttpStatus.OK);
    }

    @Operation(summary = "멤버의 아바타 수정", description = "파라미터 : accessToken (리퀘스트헤더)")
    @PatchMapping("/myavatar")
    private ResponseEntity updateAvatar(Principal principal, @RequestBody AvatarForm avatarForm) {
        int memberNo = Integer.parseInt(principal.getName());
        memberService.updateMemberAvatar(memberNo, avatarForm);
        return ResponseEntity.ok().build();
    }

}
