package com.troupe.backend.controller;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.member.LoginForm;
import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.service.member.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api("회원정보 REST API")
@RequestMapping("/member")
@RestController
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    private ResponseEntity register(@RequestBody MemberForm memberForm) {
        memberService.saveMember(memberForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    private ResponseEntity login(@RequestBody LoginForm loginForm) {
        Member member = memberService.login(loginForm);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("memberNo", member.getMemberNo());
        resultMap.put("accessToken", "1234");
        resultMap.put("message", SUCCESS);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
