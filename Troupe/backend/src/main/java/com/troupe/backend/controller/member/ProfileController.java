package com.troupe.backend.controller.member;

import com.troupe.backend.domain.category.Category;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.response.AvatarResponse;
import com.troupe.backend.dto.converter.MemberConverter;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.dto.feed.TagResponse;
import com.troupe.backend.dto.likability.LikabilityResponse;
import com.troupe.backend.dto.member.response.MemberInfoResponse;
import com.troupe.backend.dto.member.response.MemberWithLikabilityResponse;
import com.troupe.backend.dto.performance.InterestCategoryResponse;
import com.troupe.backend.dto.performance.ProfilePfResponse;
import com.troupe.backend.dto.performance.ProfilePfSaveResponse;
import com.troupe.backend.service.feed.FeedService;
import com.troupe.backend.service.member.FollowService;
import com.troupe.backend.service.member.LikabilityService;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.service.performance.PerformanceSaveService;
import com.troupe.backend.service.performance.PerformanceService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@CrossOrigin
@Api("회원 프로필 REST API")
@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final MemberService memberService;

    private final FollowService followService;

    private final LikabilityService likabilityService;

    private final FeedService feedService;

    private final PerformanceSaveService performanceSaveService;

    private final PerformanceService performanceService;

    @Operation(summary = "팔로우하기", description = "파라미터 : accessToken(리퀘스트헤더), profileMemberNo(패스배리어블)")
    @PostMapping("/{profileMemberNo}/follow")
    public ResponseEntity follow(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        followService.follow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "언팔로우하기", description = "파라미터 : accessToken(리퀘스트헤더), profileMemberNo(패스배리어블)")
    @DeleteMapping("/{profileMemberNo}/follow")
    public ResponseEntity unfollow(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        followService.unfollow(starMemberNo, fanMemberNo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "현재 팔로우 여부 조회", description = "파라미터 : accessToken(리퀘스트헤더), profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/follow")
    public ResponseEntity isFollowing(Principal principal, @PathVariable int profileMemberNo) {
        int fanMemberNo = Integer.parseInt(principal.getName());
        int starMemberNo = profileMemberNo;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.IS_FOLLOWING, followService.isFollowing(starMemberNo, fanMemberNo));
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인을 팔로우중인 팬 리스트 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/follow/fans/list")
    public ResponseEntity getFanList(@PathVariable int profileMemberNo) {
        List<Member> fans = followService.findAllFans(profileMemberNo);
        List<MemberInfoResponse> response = MemberConverter.convertMemberListToMemberInfoResponseList(fans);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.FAN_LIST, response);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인이 팔로우중인 스타 리스트 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/follow/stars/list")
    public ResponseEntity getStarList(@PathVariable int profileMemberNo) {
        List<Member> stars = followService.findAllStars(profileMemberNo);
        List<MemberInfoResponse> response = MemberConverter.convertMemberListToMemberInfoResponseList(stars);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.STAR_LIST, response);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인을 팔로우중인 팬 수 카운트", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/follow/fans/count")
    public ResponseEntity getFanCount(@PathVariable int profileMemberNo) {
        int fanCount = (int) followService.countFans(profileMemberNo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.FAN_COUNT, fanCount);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    @Operation(summary = "프로필 주인과 로그인중인 유저의 호감도 조회(레벨, 순위 포함)", description = "파라미터 : accessToken(리퀘스트헤더), profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/likability")
    public ResponseEntity getLikability(Principal principal, @PathVariable int profileMemberNo) {
        int starMemberNo = profileMemberNo;
        int fanMemberNo = Integer.parseInt(principal.getName());

        // 호감도 조회
        LikabilityResponse likabilityResponse = likabilityService.getLikabilityResponse(starMemberNo, fanMemberNo);

        return new ResponseEntity(likabilityResponse, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인이 가장 호감도가 높은 3명의 스타를 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/likability/topstars")
    public ResponseEntity getTop3StarList(@PathVariable int profileMemberNo) {
        int fanMemberNo = profileMemberNo;
        List<Likability> likabilities = likabilityService.getTop3StarList(fanMemberNo);

        List<MemberWithLikabilityResponse> top3Stars = new ArrayList<>();
        for (Likability likability : likabilities) {
            Member starMember = likability.getStarMember();

            LikabilityResponse likabilityResponse = likabilityService.getLikabilityResponse(starMember.getMemberNo(), fanMemberNo);

            MemberWithLikabilityResponse memberWithLikabilityResponse = new MemberWithLikabilityResponse(new MemberInfoResponse(starMember), new AvatarResponse(starMember), likabilityResponse);
            top3Stars.add(memberWithLikabilityResponse);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.TOP_3_STARS, top3Stars);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인에게 가장 호감도가 높은 100명의 팬을 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/likability/topfans")
    public ResponseEntity getTop100FanList(@PathVariable int profileMemberNo) {
        int starMemberNo = profileMemberNo;

        List<Likability> likabilities = likabilityService.getTop100FanList(starMemberNo);

        List<MemberWithLikabilityResponse> top100Fans = new ArrayList<>();
        for (Likability likability : likabilities) {
            Member fanMember = likability.getFanMember();

            LikabilityResponse likabilityResponse = likabilityService.getLikabilityResponse(starMemberNo, fanMember.getMemberNo());

            MemberWithLikabilityResponse memberWithLikabilityResponse = new MemberWithLikabilityResponse(new MemberInfoResponse(fanMember), new AvatarResponse(fanMember), likabilityResponse);
            top100Fans.add(memberWithLikabilityResponse);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.TOP_100_FANS, top100Fans);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    @Operation(summary = "프로필 주인이 가장 관심이 있는 태그 4개를 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/interest/tag")
    public ResponseEntity getTop4InterestTagList(@PathVariable int profileMemberNo) {
        List<Tag> top4InterestTags = feedService.getTop4InterestTagList(profileMemberNo);

        List<TagResponse> tagResponseList = new ArrayList<>();
        for (Tag t : top4InterestTags) {
            tagResponseList.add(TagResponse.builder().tagNo(t.getTagNo()).tagName(t.getName()).build());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.TOP_4_INTEREST_TAG_LIST, tagResponseList);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인이 공연 카테고리에 대해 갖는 관심도를 조회", description = "파라미터 : profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/interest/category")
    public ResponseEntity getInterestCategoryList(@PathVariable int profileMemberNo) {
        // 카운트한 결과를 맵으로 받아옴
        Map<Category, Integer> categoryCount = performanceSaveService.countInterestTags(profileMemberNo);

        // 리스폰스용 DTO 객체 생성
        List<InterestCategoryResponse> interestCategoryResponseList = new ArrayList<>();
        for (Category category : categoryCount.keySet()) {
            InterestCategoryResponse interestCategoryResponse = InterestCategoryResponse.builder()
                    .categoryNo(category.getId())
                    .bigCategory(category.getBigCategory())
                    .smallCategory(category.getSmallCategory())
                    .codeName(category.getCodeName())
                    .count(categoryCount.get(category))
                    .build();

            interestCategoryResponseList.add(interestCategoryResponse);
        }

        // 카테고리 번호순 정렬
        Collections.sort(interestCategoryResponseList, new Comparator<InterestCategoryResponse>() {
            @Override
            public int compare(InterestCategoryResponse icr1, InterestCategoryResponse icr2) {
                return icr1.getCategoryNo() - icr2.getCategoryNo();
            }
        });

        // 리스폰스 엔티티 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.INTEREST_CATEGORY_LIST, interestCategoryResponseList);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인의 공연 작성 목록", description = "파라미터: profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/myperf/list")
    public ResponseEntity<List<ProfilePfResponse>> profilePfList(Principal principal,
                                                                 @PathVariable int profileMemberNo,
                                                                 int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 6);
        List<ProfilePfResponse> profilePfResponseList = performanceService.findRegisteredList(profileMemberNo, pageRequest);
        return ResponseEntity.ok().body(profilePfResponseList);
    }
    
    @Operation(summary = "프로필 주인의 공연 북마크 목록", description = "파라미터: profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/saveperf/list")
    public ResponseEntity<List<ProfilePfSaveResponse>> profilePfSaveList(Principal principal,
                                                                         @PathVariable int profileMemberNo,
                                                                         int pageNumber) {
        //  profile service
        PageRequest pageRequest = PageRequest.of(pageNumber, 6);
        List<ProfilePfSaveResponse> profileSaveResponseList = performanceSaveService.findSavedList(profileMemberNo, pageRequest);
        return ResponseEntity.ok().body(profileSaveResponseList);
    }

    @Operation(summary = "프로필 주인의 피드 작성 목록", description = "파라미터: profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/myfeed/list")
    public ResponseEntity getMyFeedList(Principal principal, @PathVariable int profileMemberNo, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 6);
//            Slice<FeedResponse> feedResponses = null;
        List<FeedResponse> feedResponse = feedService.selectAllByMember(profileMemberNo, pageRequest);

        return new ResponseEntity(feedResponse, HttpStatus.OK);
    }

    @Operation(summary = "프로필 주인의 피드 저장 목록", description = "파라미터: profileMemberNo(패스배리어블)")
    @GetMapping("/{profileMemberNo}/savefeed/list")
    public ResponseEntity getSaveFeedList(Principal principal, @PathVariable int profileMemberNo, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 6);
//            Slice<FeedResponse> feedResponses = null;
        List<FeedResponse> feedResponse = feedService.selectAll("save", profileMemberNo, pageRequest);

        return new ResponseEntity(feedResponse, HttpStatus.OK);
    }
}


