package com.troupe.backend.controller.member;

import com.troupe.backend.service.member.FollowService;
import com.troupe.backend.service.member.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.troupe.backend.util.MyUtil.getMemberNoFromRequestHeader;

@CrossOrigin
@Api("회원 프로필 REST API")
@RequestMapping("/profile")
@RestController
public class ProfileController {
    private MemberService memberService;

    private FollowService followService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setFollowService(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{profileMemberNo}/follow")
    public ResponseEntity follow(@RequestHeader Map<String, Object> requestHeader, @PathVariable int profileMemberNo) {
        int fanMemberNo = getMemberNoFromRequestHeader(requestHeader);
        int starMemberNo = profileMemberNo;

        followService.follow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{profileMemberNo}/follow")
    public ResponseEntity unfollow(@RequestHeader Map<String, Object> requestHeader, @PathVariable int profileMemberNo) {
        int fanMemberNo = getMemberNoFromRequestHeader(requestHeader);
        int starMemberNo = profileMemberNo;

        followService.unfollow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    // 여기 짜다 말았음
    @PostMapping("/{profileMemberNo}/follow")
    public ResponseEntity isFollowing(@RequestHeader Map<String, Object> requestHeader, @PathVariable int profileMemberNo) {
        int fanMemberNo = getMemberNoFromRequestHeader(requestHeader);
        int starMemberNo = profileMemberNo;

        followService.isFollowing(starMemberNo, fanMemberNo);

        return ResponseEntity.ok().build();
    }
}


