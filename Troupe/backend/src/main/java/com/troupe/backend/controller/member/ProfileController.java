package com.troupe.backend.controller.member;

import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.likability.LikabilityLevel;
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
import java.util.Optional;

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


    @GetMapping("/{profileMemberNo}/likability")
    public ResponseEntity getLikability(Principal principal, @PathVariable int profileMemberNo) {
        int starMemberNo = profileMemberNo;
        int fanMemberNo = Integer.parseInt(principal.getName());

        // 현재 호감도 조회
        Optional<Likability> foundLikability = likabilityService.findByStarMemberNoAndFanMemberNo(profileMemberNo, fanMemberNo);

        int level = 0;
        int exp = 0;
        int requiredExpNow = 0;
        int requiredExpNext = 0;

        // 현재 호감도의 레벨 조회
        if (foundLikability.isPresent()) {
            exp = foundLikability.get().getExp();
            level = likabilityService.getLikabilityLevel(exp);

            Optional<LikabilityLevel> foundNowLevel = likabilityService.findById(level);

            if (foundNowLevel.isPresent()) {
                requiredExpNow = foundNowLevel.get().getRequiredExp();
            }
        }

        // 다음 레벨까지 필요 경험치 조회
        int nextLevel = level + 1;
        Optional<LikabilityLevel> foundNextLikabilityLevel = likabilityService.findById(nextLevel);
        if (foundNextLikabilityLevel.isPresent()) {
            requiredExpNext = foundNextLikabilityLevel.get().getRequiredExp();
        }

        // 반환값 리턴
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.EXP, exp);
        resultMap.put(MyConstant.LEVEL, level);
        resultMap.put(MyConstant.REQUIRED_EXP_NOW, requiredExpNow);
        resultMap.put(MyConstant.REQUIRED_EXP_NEXT, requiredExpNext);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{profileMemberNo}/likability/rank")
    public ResponseEntity getLikabilityRank(Principal principal, @PathVariable int profileMemberNo) {
        int starMemberNo = profileMemberNo;
        int fanMemberNo = Integer.parseInt(principal.getName());

        // 현재 호감도 조회
        Optional<Likability> foundLikability = likabilityService.findByStarMemberNoAndFanMemberNo(profileMemberNo, fanMemberNo);
        int exp = (foundLikability.isPresent()) ? foundLikability.get().getExp() : 0;

        int rank = likabilityService.getRank(starMemberNo, exp) + 1;
        // 반환값 리턴
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.RANK, rank);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    // TODO : 이 아래로는 미구현. 쿼리 필요
//    @GetMapping("/{profileMemberNo}/interest/tag")
//    public ResponseEntity getInterestTagList(@PathVariable int profileMemberNo) {
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(MyConstant.TAG_LIST, resultMap);
//        return new ResponseEntity(resultMap, HttpStatus.OK);
//    }
//


}


