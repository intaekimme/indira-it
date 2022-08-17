package com.troupe.backend.controller.performance;


import com.troupe.backend.dto.performance.response.*;
import com.troupe.backend.dto.performance.form.PerformanceForm;
import com.troupe.backend.dto.performance.form.PerformanceModifyForm;
import com.troupe.backend.dto.performance.form.PfReviewForm;
import com.troupe.backend.service.performance.*;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@CrossOrigin
@Api("공연 REST API")
@RequestMapping("/perf")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PerformanceController {
    private final PerformanceService performanceService;
    private final PerformanceSaveService performanceSaveService;
    private final PerformanceReviewService performanceReviewService;


    /**
     * 공연 등록
     * @param principal
     * @param performanceform
     * @return
     * @throws IOException
     */
    @Operation(summary = "공연 등록", description = "파라미터: 공연 등록 폼")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity register(Principal principal,
                                   @ModelAttribute @Valid PerformanceForm performanceform)
            throws IOException {
        log.info(performanceform.toString());
        int memberNo = Integer.parseInt(principal.getName());
        performanceService.register(memberNo, performanceform);
        return ResponseEntity.created(URI.create(MyConstant.WEB_SITE_URL+"/perf/list")).build();
    }


    /**
     * 공연 수정
     * @param principal
     * @param pfNo
     * @param performanceModifyForm
     * @return
     */
    @Operation(summary = "공연 수정", description = "파라미터: 공연 번호, 공연 수정 폼")
    @PostMapping(value = "/{pfNo}/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity modify(Principal principal,
                                 @PathVariable int pfNo,
                                 @ModelAttribute @Valid PerformanceModifyForm performanceModifyForm)
            throws Exception {

        log.info(performanceModifyForm.toString());
        int memberNo = Integer.parseInt(principal.getName());
        performanceService.modify(memberNo, pfNo, performanceModifyForm);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 삭제
     * @param principal
     * @param pfNo
     * @return
     */
    @Operation(summary = "공연 삭제", description = "파라미터: 공연 번호")
    @PatchMapping("/{pfNo}/del")
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
    @Operation(summary = "공연 목록 조회, 최근 생성 순", description = "파라미터: 페이지 번호")
    @GetMapping("/list")
    public ResponseEntity<List<PerformanceResponse>> performanceList(int pageNumber){
        Pageable sortedByCreatedTime = PageRequest.of(pageNumber,6, Sort.by("createdTime").descending());
        List<PerformanceResponse> performanceResponseList =  performanceService.findAll( sortedByCreatedTime);
        return ResponseEntity.ok().
                body(performanceResponseList);
    }

    /**
     * 공연 목록 조회(검색 및 필터 기능 포함)
     * @param condition 필터 (이름으로 검색 {condition : nickname}, 제목+내용으로 검색 {condition : title})
     * @param keyword 질의어
     * @return
     */
    @Operation(summary = "공연 목록 조회(검색 및 필터 기능 포함)", description = "파라미터: 필터 (이름으로 검색 {condition : nickname}, 제목+내용으로 검색 {condition : title}), 질의어")
    @GetMapping("/list/search")
    public ResponseEntity<List<PerformanceResponse>> performanceQueryList(@RequestParam String condition,
                                                                          @RequestParam String keyword){
        List<PerformanceResponse> performanceResponseList =  performanceService.findQueryAll(condition, keyword);
        return ResponseEntity.ok()
                .body(performanceResponseList);
    }

    /**
     * 공연 조회 상세
     * @param pfNo
     * @return
     */
    @Operation(summary = "공연 조회 상세", description = "파라미터: 공연 번호")
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
    @Operation(summary = "공연 북마크 저장", description = "파라미터: 공연 번호")
    @PostMapping("/{pfNo}/save")
    public ResponseEntity performanceSave(Principal principal,
                                          @PathVariable int pfNo){
        int memberNo = Integer.parseInt(principal.getName());
        performanceSaveService.save(memberNo, pfNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 공연 북마크 삭제
     * @param principal
     * @param pfNo
     * @return
     */
    @Operation(summary = "공연 북마크 삭제", description = "파라미터: 공연 번호")
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
     * @param body
     * @return
     */
    @Operation(summary = "공연 후기 작성", description = "파라미터: 공연 번호, 내용, 부모댓글번호-선택")
    @PostMapping("/{pfNo}/review")
    public ResponseEntity<PfReviewResponse> registerReview(Principal principal,
                                                           @PathVariable String pfNo,
                                                           @RequestParam(required = false) Integer parentCommentNo,
                                                           @RequestBody Map<String, String> body){
        System.out.println(body.get("content"));
        int memberNo = Integer.parseInt(principal.getName());
        
        PfReviewForm request = PfReviewForm.builder()
                .pfNo(Integer.parseInt(pfNo))
                .memberNo(memberNo)
                .content(body.get("content"))
                .build();

        request.setParentReviewNo(Objects.requireNonNullElse(parentCommentNo, 0));

        PfReviewResponse response = performanceReviewService.add(request);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 공연후기삭제
     * @param principal
     * @param pfNo
     * @param reviewNo
     * @return
     */
    @Operation(summary = "공연 후기 삭제", description = "파라미터: 공연 번호, 후기 번호")
    @PatchMapping("/{pfNo}/review/{reviewNo}/del")
    public ResponseEntity deleteReview(Principal principal,
                                       @PathVariable int pfNo,
                                       @PathVariable int reviewNo){
        try{
            int memberNo = Integer.parseInt(principal.getName());
            performanceReviewService.delete(pfNo, reviewNo);
            return ResponseEntity.ok().build();
        }catch(NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 공연후기목록
     * @param pfNo
     * @return
     */
    @Operation(summary = "공연 후기 목록", description = "파라미터: 공연 번호")
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
    @Operation(summary = "공연 후기 수정", description = "파라미터: 후기 번호, 내용")
    @PatchMapping("/{pfNo}/review/{reviewNo}/modify")
    public ResponseEntity<PfReviewResponse> modifyPfReview(Principal principal,
                                         @PathVariable int pfNo,
                                         @PathVariable int reviewNo,
                                         @RequestBody Map<String, String> content){

        PfReviewResponse response = performanceReviewService.modify(pfNo, reviewNo, content.get("content"));
        return ResponseEntity.ok().body(response);
    }

    /**
     * 공연 후기 대댓글 목록
     * @param pfNo
     * @param reviewNo
     * @return
     */
    @Operation(summary = "공연 후기 대댓글 목록", description = "파라미터: 공연 번호, 리뷰 번호")
    @GetMapping("/{pfNo}/review/{reviewNo}/list")
    public ResponseEntity<List<PfReviewResponse>> findPfChildReviewList( @PathVariable int pfNo,
                                                                        @PathVariable int reviewNo){

        List<PfReviewResponse> pfReviewResponseList = performanceReviewService.findPfChildReviewList(pfNo, reviewNo);
        return ResponseEntity.ok().body(pfReviewResponseList);
    }

    //  =================================================================================
    //  profile controller
    //  =================================================================================

    /**
     * 프로필, 유저의 공연 북마크 목록
     * @param principal
     * @param profileMemberNo
     * @return
     */
    @Operation(summary = "프로필, 유저의 공연 북마크 목록", description = "파라미터: 유저 프로필 번호")
    @GetMapping("/{profileMemberNo}/saveperf/list")
    public ResponseEntity<List<ProfilePfResponse>> profilePfSaveList(Principal principal,
                                                                         @PathVariable int profileMemberNo,
                                                                         int pageNumber){
        //  profile service
        PageRequest pageRequest = PageRequest.of(pageNumber,6);
        List<ProfilePfResponse> profileResponseList = performanceSaveService.findSavedList(profileMemberNo, pageRequest);
        return ResponseEntity.ok().body(profileResponseList);
    }

    /**
     * 프로필, 유저가 등록한 공연 목록 불러오기
     * @param principal
     * @param profileMemberNo
     * @return
     */
    @Operation(summary = "프로필, 유저가 등록한 공연 목록 불러오기", description = "파라미터: 유저 프로필 번호")
    @GetMapping("/{profileMemberNo}/myperf/list")
    public ResponseEntity<List<ProfilePfResponse>> profilePfList(Principal principal,
                                                                 @PathVariable int profileMemberNo,
                                                                 int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber,6);
        List<ProfilePfResponse> profilePfResponseList = performanceService.findRegisteredList(profileMemberNo, pageRequest);
        return ResponseEntity.ok().body(profilePfResponseList);
    }


    @Operation(summary = "공연 북마크 여부 불러오기", description = "파라미터 공연 번호")
    @GetMapping("/{PfNo}/save/now")
    public ResponseEntity pfSavecheck(Principal principal, @PathVariable int PfNo) throws IOException {
        try{
            return new ResponseEntity(performanceSaveService.checkPfSave(Integer.parseInt(principal.getName()),PfNo), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("feedSave check FAIL", HttpStatus.BAD_REQUEST);
        }
    }

}
