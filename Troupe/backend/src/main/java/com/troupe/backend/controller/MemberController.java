package com.troupe.backend.controller;

import com.troupe.backend.dto.member.MemberForm;
import com.troupe.backend.service.member.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity register(@RequestBody MemberForm memberForm) {
        memberService.saveMember(memberForm);
        return ResponseEntity.ok().build();
    }
}
