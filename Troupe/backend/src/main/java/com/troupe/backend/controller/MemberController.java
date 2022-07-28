package com.troupe.backend.controller;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.member.LoginForm;
import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api("회원정보 REST API")
@RequestMapping("/member")
@RestController
public class MemberController {
    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    private ResponseEntity register(@RequestParam String email,
                                    @RequestParam String password,
                                    @RequestParam String nickname,
                                    @RequestParam String description,
                                    @RequestParam MultipartFile profileImage) throws IOException {
        MemberForm memberForm = MemberForm.builder().email(email).password(password).nickname(nickname).description(description).profileImage(profileImage).build();
//    private ResponseEntity register(@RequestBody MemberForm memberForm) throws IOException {
        memberService.saveMember(memberForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    private ResponseEntity login(@RequestBody LoginForm loginForm) {
        Member member = memberService.login(loginForm);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MEMBER_NO, member.getMemberNo());
        resultMap.put(MyConstant.ACCESS_TOKEN, "1234");
        resultMap.put(MyConstant.MESSAGE, MyConstant.SUCCESS);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @PatchMapping("/{memberNo}")
    private ResponseEntity updateMember(@PathVariable int memberNo, @RequestBody MemberForm memberForm) throws IOException {
        Member foundMember = memberService.findById(memberNo).get();
        memberService.updateMember(memberNo, memberForm);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{memberNo}/del")
    private ResponseEntity deleteMember(@PathVariable int memberNo) {
        memberService.deleteMember(memberNo);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup/email")
    private ResponseEntity checkDuplicateEmail(@RequestBody String email) {
        Map<String, Object> resultMap = new HashMap<>();

        if (memberService.isDuplicateEmail(email)) {
            resultMap.put(MyConstant.MESSAGE, "이미 사용 중인 이메일입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.CONFLICT);
        } else {
            resultMap.put(MyConstant.MESSAGE, "사용 가능한 이메일입니다\n");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }


    }

    @PostMapping("/signup/nickname")
    private ResponseEntity checkDuplicateNickname(@RequestBody String nickname) {
        Map<String, Object> resultMap = new HashMap<>();

        if (memberService.isDuplicateNickname(nickname)) {
            resultMap.put(MyConstant.MESSAGE, "이미 사용 중인 닉네임입니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.CONFLICT);
        } else {
            resultMap.put(MyConstant.MESSAGE, "사용 가능한 닉네임입니다\n");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }
    }

}
