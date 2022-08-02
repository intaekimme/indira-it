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
import java.util.List;
import java.util.Map;

import static com.troupe.backend.util.MyUtil.getMemberNoFromRequestHeader;

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
     * @param requestHeader
     * @param performanceform
     * @return
     */
    @PostMapping
    public ResponseEntity register(@RequestHeader Map<String, Object> requestHeader,
                                   @RequestPart(value = "performanceForm") PerformanceForm performanceform,
                                   @RequestPart(value = "image") List<MultipartFile> multipartFileList) throws IOException {
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceService.register(memberNo, performanceform, multipartFileList);
        return ResponseEntity.ok().build();
    }


    /**
     * 공연 수정
     * @param requestHeader
     * @param performanceform
     * @return
     */
    @PatchMapping("{pfNo}/modify")
    public ResponseEntity modify(@PathVariable int pfNo, @RequestHeader Map<String, Object> requestHeader, @RequestBody PerformanceForm performanceform){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceService.modify(pfNo, memberNo, performanceform);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연삭제
     * @param pfNo
     * @param requestHeader
     * @return
     */
    @PatchMapping("{pfNo}/del")
    public ResponseEntity delete(@PathVariable int pfNo, @RequestHeader Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceService.delete(pfNo, memberNo);
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
     * @param pfNo
     * @param requestHeader
     * @return
     */
    @PostMapping("/{pfNo}/save")
    public ResponseEntity performanceSave(@PathVariable int pfNo, @RequestHeader Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceSaveService.save(pfNo, memberNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 북마크 저장 삭제
     * @param pfNo
     * @param requestHeader
     * @return
     */
    @PatchMapping("/{pfNo}/save/del")
    public ResponseEntity performanceSaveDelete(@PathVariable int pfNo, @RequestHeader Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceSaveService.delete(pfNo, memberNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연후기작성
     * @param pfNo
     * @param requestHeader
     * @param content
     * @return
     */
    @PostMapping("/{pfNo}/review")
    public ResponseEntity registerReview(@PathVariable int pfNo,
                                         @RequestHeader Map<String, Object> requestHeader,
                                         @RequestParam String content){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        performanceReviewService.add(pfNo, memberNo, content);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{pfNo}/review/{reviewNo}/del")
    public ResponseEntity deleteReview(@PathVariable int pfNo,
                                       @PathVariable int reviewNo){
        performanceReviewService.delete(pfNo, reviewNo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pfNo}/review/list")
    public ResponseEntity<List<PfReviewResponse>> findPfReviewList(@PathVariable int pfNo,
                                             @RequestHeader Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        List<PfReviewResponse> pfReviewResponseList = performanceReviewService.findPfReviewList(pfNo, memberNo);
        return ResponseEntity.ok().body(pfReviewResponseList);
    }

    //  =================================================================================
    //  profile controller
    //  =================================================================================

    /**
     * 프로필 주인의 공연 북마크 목록
     * @param profileMemberNo
     * @param requestHeader
     * @return
     */
    @GetMapping("/{profileMemberNo}/saveperf/list")
    public ResponseEntity<List<ProfilePfSaveResponse>> profilePfSaveList(@PathVariable int profileMemberNo, @RequestHeader Map<String, Object> requestHeader){
        //  profile service
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        List<ProfilePfSaveResponse> profileSaveResponseList = performanceSaveService.findSavedList(memberNo);
        return ResponseEntity.ok().body(profileSaveResponseList);
    }

    //  프로필 주인이 등록한 공연 목록 불러오기
    @GetMapping("/{profileMemberNo}/myperf/list")
    public ResponseEntity<List<ProfilePfResponse>> profilePfList(@PathVariable int profileMemberNo, @RequestHeader Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        List<ProfilePfResponse> profilePfResponseList = performanceService.findRegisteredList(memberNo);
        return ResponseEntity.ok().body(profilePfResponseList);
    }

}
