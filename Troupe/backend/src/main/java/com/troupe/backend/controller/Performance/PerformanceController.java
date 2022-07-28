package com.troupe.backend.controller.Performance;


import com.troupe.backend.dto.Performance.Performanceform;
import com.troupe.backend.service.Performance.PerformanceService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Api("공연 REST API")
@RequestMapping("/perf")
@RestController
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    /**
     * 공연 등록
     * @param requestHeader
     * @param performanceform
     * @return
     */
    @PostMapping
    public ResponseEntity register(@RequestHeader Map<String, Object> requestHeader, @RequestBody Performanceform performanceform){
        int memberNo = (int) requestHeader.get(MyConstant.MEMBER_NO);
        performanceService.addPerformance(memberNo, performanceform);
        return ResponseEntity.ok().build();
    }


    /**
     * 공연 수정
     * @param requestHeader
     * @param performanceform
     * @return
     */
    @PatchMapping("{pfNo}/modify")
    public ResponseEntity modify(@PathVariable int pfNo, @RequestHeader Map<String, Object> requestHeader, @RequestBody Performanceform performanceform){
        int memberNo = (int) requestHeader.get(MyConstant.MEMBER_NO);
        performanceService.updatePerformance(memberNo, pfNo, performanceform);
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
        performanceService.deletePerformance(memberNo, pfNo);
        return ResponseEntity.ok().build();
    }
}
