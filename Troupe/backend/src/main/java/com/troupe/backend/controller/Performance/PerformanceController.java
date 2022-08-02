package com.troupe.backend.controller.Performance;


import com.troupe.backend.dto.Performance.*;
import com.troupe.backend.service.Performance.*;
import com.troupe.backend.util.S3FileUploadService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;



@CrossOrigin
@Api("공연 REST API")
@RequestMapping("/perf")
@RestController
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;
    private final PerformancePriceService performancePriceService;
    private final PerformanceImageService performanceImageService;
    private final PerformanceSaveService performanceSaveService;
    private final PerformanceReviewService performanceReviewService;
    private final S3FileUploadService s3FileUploadService;

    /**
     * 공연 등록
     * @param principal
     * @param performanceform
     * @param multipartFileList
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity register(Principal principal,
                                   @RequestPart(value = "performanceForm") PerformanceForm performanceform,
                                   @RequestPart(value = "image") List<MultipartFile> multipartFileList) throws IOException {
        int memberNo = Integer.parseInt(principal.getName());
        performanceService.register(memberNo, performanceform, multipartFileList);
        return ResponseEntity.ok().build();
    }


    /**
     * 공연 수정
     * @param principal
     * @param pfNo
     * @param performanceform
     * @return
     */
    @PatchMapping("{pfNo}/modify")
    public ResponseEntity modify(Principal principal,
                                 @PathVariable int pfNo,
                                 @RequestBody PerformanceForm performanceform){
        int memberNo = Integer.parseInt(principal.getName());
        performanceService.modify(memberNo, pfNo, performanceform);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 삭제
     * @param principal
     * @param pfNo
     * @return
     */
    @PatchMapping("{pfNo}/del")
    public ResponseEntity delete(Principal principal,
                                 @PathVariable int pfNo){
        int memberNo = Integer.parseInt(principal.getName());
        performanceService.delete(memberNo, pfNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 목록 조회, 최근 생성 순
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<PerformanceResponse>> performanceList(){
        List<PerformanceResponse> performanceResponseList =  performanceService.findAll();
        return ResponseEntity.ok().
                body(performanceResponseList);
    }

    /**
     * 공연 목록 조회(검색 및 필터 기능 포함)
     * @param requestHeader : id 정보
     * @param performanceSearchForm : key, queryWord, genere(?)
     * @return
     */
//    @GetMapping("/list")
//    public ResponseEntity performanceList2(@RequestHeader Map<String, Object> requestHeader, @RequestBody PerformanceSearchForm performanceSearchForm){
//        return null;
//    }

    /**
     * 공연 조회 상세
     * @param pfNo
     * @return
     */
    @GetMapping("/{pfNo}")
    public ResponseEntity<PerformanceDetailResponse> performanceDetail(@PathVariable int pfNo){
        PerformanceDetailResponse performanceDetailResponse = performanceService.detail(pfNo);
        return ResponseEntity.ok()
                .body(performanceDetailResponse);
    }

    /**
     * 공연 북마크 저장
     * @param principal
     * @param pfNo
     * @return
     */
    @PostMapping("/{pfNo}/save")
    public ResponseEntity performanceSave(Principal principal,
                                          @PathVariable int pfNo){
        int memberNo = Integer.parseInt(principal.getName());
        performanceSaveService.save(memberNo, pfNo);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{pfNo}/save/del")
    public ResponseEntity performanceSaveDelete(Principal principal,
                                                @PathVariable int pfNo){
        int memberNo = Integer.parseInt(principal.getName());
        performanceSaveService.delete(memberNo, pfNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연후기작성
     * @param principal
     * @param pfNo
     * @param content
     * @return
     */
    @PostMapping("/{pfNo}/review")
    public ResponseEntity registerReview(Principal principal,
                                         @PathVariable int pfNo,
                                         @RequestParam String content){
        int memberNo = Integer.parseInt(principal.getName());
        performanceReviewService.add(pfNo, memberNo, content);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연후기삭제
     * @param principal
     * @param pfNo
     * @param reviewNo
     * @return
     */
    @PatchMapping("/{pfNo}/review/{reviewNo}/del")
    public ResponseEntity deleteReview(Principal principal,
                                       @PathVariable int pfNo,
                                       @PathVariable int reviewNo){
        int memberNo = Integer.parseInt(principal.getName());
        performanceReviewService.delete(pfNo, reviewNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연후기목록
     * @param pfNo
     * @return
     */
    @GetMapping("/{pfNo}/review/list")
    public ResponseEntity<List<PfReviewResponse>> findPfReviewList(@PathVariable int pfNo){
        List<PfReviewResponse> pfReviewResponseList = performanceReviewService.findPfReviewList(pfNo);
        return ResponseEntity.ok().body(pfReviewResponseList);
    }

    /**
     * 공연후기수정
     * @param pfNo
     * @param reviewNo
     * @param content
     * @return
     */
    @PatchMapping("/{pfNo}/review/{reviewNo}/modify")
    public ResponseEntity modifyPfReview(Principal principal,
                                         @PathVariable int pfNo,
                                         @PathVariable int reviewNo,
                                         @RequestParam String content){
        performanceReviewService.modify(pfNo, reviewNo, content);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 후기 대댓글 목록
     * @param principal
     * @param pfNo
     * @param reviewNo
     * @return
     */
    @GetMapping("/{pfNo}/review/{reviewNo}/list")
    public ResponseEntity<List<PfReviewResponse>> findPfChildReviewList(Principal principal,
                                                                        @PathVariable int pfNo,
                                                                        @PathVariable int reviewNo){
        int memberNo = Integer.parseInt(principal.getName());
        List<PfReviewResponse> pfReviewResponseList = performanceReviewService.findPfChildReviewList(pfNo, reviewNo, memberNo);
        return ResponseEntity.ok().body(pfReviewResponseList);
    }

    //  =================================================================================
    //  profile controller
    //  =================================================================================

    /**
     * 프로필 주인의 공연 북마크 목록
     * @param principal
     * @param profileMemberNo
     * @return
     */
    @GetMapping("/{profileMemberNo}/saveperf/list")
    public ResponseEntity<List<ProfilePfSaveResponse>> profilePfSaveList(Principal principal,
                                                                         @PathVariable int profileMemberNo){
        //  profile service
        int memberNo = Integer.parseInt(principal.getName());
        List<ProfilePfSaveResponse> profileSaveResponseList = performanceSaveService.findSavedList(memberNo);
        return ResponseEntity.ok().body(profileSaveResponseList);
    }

    /**
     * 프로필 주인이 등록한 공연 목록 불러오기
     * @param principal
     * @param profileMemberNo
     * @return
     */
    @GetMapping("/{profileMemberNo}/myperf/list")
    public ResponseEntity<List<ProfilePfResponse>> profilePfList(Principal principal,
                                                                 @PathVariable int profileMemberNo){
        int memberNo = Integer.parseInt(principal.getName());
        List<ProfilePfResponse> profilePfResponseList = performanceService.findRegisteredList(memberNo);
        return ResponseEntity.ok().body(profilePfResponseList);
    }

}