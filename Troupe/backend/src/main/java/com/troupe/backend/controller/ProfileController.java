package com.troupe.backend.controller;

import com.troupe.backend.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileController {
    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }


}
