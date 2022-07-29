package com.troupe.backend.controller.Performance;


import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.dto.Performance.PerformanceForm;
import com.troupe.backend.dto.Performance.PerformanceResponse;
import com.troupe.backend.dto.Performance.PerformanceSearchForm;
import com.troupe.backend.service.Performance.PerformancePriceService;
import com.troupe.backend.service.Performance.PerformanceService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Api("공연 REST API")
@RequestMapping("/perf")
@RestController
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;
    private final PerformancePriceService performancePriceService;

    /**
     * 공연 등록
     * @param requestHeader
     * @param performanceform
     * @return
     */
    @PostMapping
    public ResponseEntity register(@RequestHeader Map<String, Object> requestHeader, @RequestBody PerformanceForm performanceform){
        int memberNo = (int) requestHeader.get(MyConstant.MEMBER_NO);
        //  공연 기본 정보 등록 서비스 호출
        Performance performance = performanceService.addPerformance(memberNo, performanceform);
        //  공연 좌석 정보 등록 서비스 호출
        performancePriceService.addPerformancePrice(performance, performanceform);
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
        int memberNo = (int) requestHeader.get(MyConstant.MEMBER_NO);
        //  공연 기본 정보 수정 서비스 호출
        Performance performance = performanceService.updatePerformance(memberNo, pfNo, performanceform);
        //  공연 좌석 정보 수정 서비스 호출
        performancePriceService.updatePerformacePrice(performance, performanceform);
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
        int memberNo = (int) requestHeader.get(MyConstant.MEMBER_NO);
        //  공연 기본 정보 삭제 서비스 호출
        performanceService.deletePerformance(memberNo, pfNo);
        //  공연 좌석 정보 삭제 서비스 호출
        performancePriceService.deletePerformancePrice(pfNo);
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
    public ResponseEntity performanceDetail(@PathVariable int pfNo){
        return null;
    }



}
