package com.troupe.backend.controller.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.AvatarForm;
import com.troupe.backend.dto.member.AvatarResponse;
import com.troupe.backend.dto.member.LoginForm;
import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.dto.member.MemberInfoResponse;
import com.troupe.backend.security.JwtTokenProvider;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api("회원정보 REST API")
@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
//    private ResponseEntity register(@RequestParam String email,
//                                    @RequestParam String password,
//                                    @RequestParam String nickname,
//                                    @RequestParam String description,
//                                    @RequestParam MultipartFile profileImage) throws IOException {
//        MemberForm memberForm = MemberForm.builder().email(email).password(password).nickname(nickname).description(description).profileImage(profileImage).build();
    private ResponseEntity register(@RequestBody MemberForm memberForm) throws IOException {
        System.out.println(memberForm.toString());

        memberService.saveMember(memberForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    private ResponseEntity login(@RequestBody LoginForm loginForm) {
        Member member = memberService.login(loginForm);

        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MEMBER_NO, member.getMemberNo());
        resultMap.put(MyConstant.ACCESS_TOKEN, token);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @PatchMapping("/{memberNo}")
    private ResponseEntity updateMember(@PathVariable int memberNo, @RequestBody MemberForm memberForm) throws IOException {
        System.out.println(memberForm.toString());
        memberService.updateMember(memberNo, memberForm);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{memberNo}/del")
    private ResponseEntity deleteMember(@PathVariable int memberNo) {
        memberService.deleteMember(memberNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberNo}")
    private ResponseEntity getMemberInfo(@PathVariable int memberNo) {
        Member foundMember = memberService.findById(memberNo).get();
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(foundMember);
        return new ResponseEntity(memberInfoResponse, HttpStatus.OK);
    }

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

    @GetMapping("/{memberNo}/avatar")
    private ResponseEntity getAvatar(@PathVariable int memberNo) {
        Member foundMember = memberService.findById(memberNo).get();
        AvatarResponse avatarResponse = new AvatarResponse(foundMember);
        return new ResponseEntity(avatarResponse, HttpStatus.OK);
    }

    @PatchMapping("/{memberNo}/avatar")
    private ResponseEntity updateAvatar(@PathVariable int memberNo, @RequestBody AvatarForm avatarForm) {
        memberService.updateMemberAvatar(memberNo, avatarForm);
        return ResponseEntity.ok().build();
    }

}
