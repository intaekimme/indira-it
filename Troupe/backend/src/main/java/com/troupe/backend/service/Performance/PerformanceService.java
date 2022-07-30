package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.dto.Performance.PerformanceForm;
import com.troupe.backend.dto.Performance.PerformanceResponse;
import com.troupe.backend.exception.MemberNotFoundException;
import com.troupe.backend.exception.performance.PerformanceNotFoundException;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PerformanceService {

    private final PerfomanceImageService perfomanceImageService;
    private final PerformanceRepository performanceRepository;
    private final MemberRepository memberRepository;


    /**
     * 공연 등록 서비스
     * @param memberNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance addPerformance(int memberNo, PerformanceForm performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
       return performanceRepository.save(performanceform.createPerformanceEntity(member));
    }

    /**
     * 공연 수정 서비스
     * @param memberNo
     * @param performanceNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance updatePerformance(int memberNo, int performanceNo, PerformanceForm performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        return performanceRepository.save(performanceform.updatePerformanceEntity(member, performance));
    }

    /**
     * 공연 삭제 서비스
     * @param memberNo
     * @param performanceNo
     */
    @Transactional
    public void deletePerformance(int memberNo, int performanceNo){
//        Member member = memberRepository.findById(memberNo)
//                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
//        Performance performance = performanceRepository.findById(performanceNo)
//                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
//        Performance targetPerformance = performanceRepository.findByMemberNoAndId(member, performanceNo);
//        targetPerformance.setRemoved(true);
//        performanceRepository.save(targetPerformance);

        Performance performance = performanceRepository.findById(performanceNo).get();
        performance.setRemoved(true);
        performanceRepository.save(performance);
    }

    /**
     * 공연 번호로 특정 공연 찾기
     * @param performanceNo
     * @return
     */
    @Transactional(readOnly = true)
    public Performance findPerformanceByNo(int performanceNo){
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        return performance;
    }

    /**
     * 공연 목록 조회, 최근 등록 순으로 정렬
     * @return
     */
    @Transactional(readOnly = true)
    public List<PerformanceResponse> findAll(){
        List<Performance> performanceList = performanceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));

        List<PerformanceResponse> performanceResponseList = new ArrayList<>();

        for(Performance p : performanceList){
            List<String> imgUrlList = perfomanceImageService.findPerformanceImagesByPerformance(p);
            performanceResponseList.add(
                PerformanceResponse.PerformanceToResponse(p, imgUrlList)
            );
        }

        return performanceResponseList;
    }

}
