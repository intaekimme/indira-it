package com.troupe.backend.controller.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.MemberConverter;
import com.troupe.backend.dto.member.MemberInfoResponse;
import com.troupe.backend.service.member.FollowService;
import com.troupe.backend.service.member.LikabilityService;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api("회원 프로필 REST API")
@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final MemberService memberService;

    private final FollowService followService;

    private final LikabilityService likabilityService;

    @PostMapping("/{profileMemberNo}/follow")
    public ResponseEntity follow(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        followService.follow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{profileMemberNo}/follow")
    public ResponseEntity unfollow(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        followService.unfollow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{profileMemberNo}/follow")
    public ResponseEntity isFollowing(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.IS_FOLLOWING, followService.isFollowing(starMemberNo, fanMemberNo));
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{profileMemberNo}/follow/fans/list")
    public ResponseEntity getFanList(@PathVariable int profileMemberNo) {
        List<Member> fans = followService.findAllFans(profileMemberNo);
        List<MemberInfoResponse> response = MemberConverter.getMemberInfoResponseList(fans);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.FAN_LIST, response);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{profileMemberNo}/follow/stars/list")
    public ResponseEntity getStarList(@PathVariable int profileMemberNo) {
        List<Member> stars = followService.findAllStars(profileMemberNo);
        List<MemberInfoResponse> response = MemberConverter.getMemberInfoResponseList(stars);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.STAR_LIST, response);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{profileMemberNo}/follow/fans/count")
    public ResponseEntity getFanCount(@PathVariable int profileMemberNo) {
        int fanCount = (int) followService.countFans(profileMemberNo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.FAN_COUNT, fanCount);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    // TODO : 이 아래로는 미구현. 쿼리 필요
//    @GetMapping("/{profileMemberNo}/tag")
//    public ResponseEntity getInterestTagList(@PathVariable int profileMemberNo) {
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(MyConstant.TAG_LIST, resultMap);
//        return new ResponseEntity(resultMap, HttpStatus.OK);
//    }
//
//    @GetMapping("/{profileMemberNo}/likability")
//    public ResponseEntity getLikability(@RequestHeader Map<String, Object> requestHeader, @PathVariable int profileMemberNo) {
//        int starMemberNo = profileMemberNo;
//        int fanMemberNo = MyUtil.getMemberNoFromRequestHeader(requestHeader);
//
//        Optional<Likability> found = likabilityService.findByStarMemberNoAndFanMemberNo(profileMemberNo, fanMemberNo);
//
//        int exp = 0;
//        int level = 0;
//
//        // TODO : 경험치로 현재 레벨, 다음 레벨, 다음 레벨까지 남은 경험치 쿼리로 가져와야 함
//        if (found.isPresent()) {
//            exp = found.get().getExp();
//        }
//        else {
//
//        }
//
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(MyConstant.EXP, exp);
//        return new ResponseEntity(resultMap, HttpStatus.OK);
//    }


}


