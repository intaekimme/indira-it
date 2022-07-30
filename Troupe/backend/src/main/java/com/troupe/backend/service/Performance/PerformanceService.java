package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.dto.Performance.PerformanceForm;
import com.troupe.backend.dto.Performance.PerformanceResponse;
import com.troupe.backend.dto.converter.PerformanceConverter;
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
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Service
public class PerformanceService {

    private final PerformanceImageService performanceImageService;
    private final PerformanceRepository performanceRepository;
    private final MemberRepository memberRepository;

    private final PerformanceConverter performanceConverter;


    /**
     * 공연 기본 정보 등록 서비스
     * @param memberNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance addPerformance(int memberNo, PerformanceForm performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("존재 하지 않는 유저입니다."));
        if(performanceform.getTitle() == null || performanceform.getTitle().isBlank())
            throw new RuntimeException("공연 제목을 입력하세요");

        Performance newPerformance = performanceConverter.toPerformanceEntityWhenCreate(performanceform, member);
        return performanceRepository.save(newPerformance);
    }

    /**
     * 공연 기본 정보 수정 서비스
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
        //  기존 정보 수정
        Performance updatePerformance = performanceConverter.toPerformanceEntityWhenUpdate(performanceform, member, performance);
        return performanceRepository.save(updatePerformance);
    }

    /**
     * 공연 기본 정보 삭제 서비스
     * @param memberNo
     * @param performanceNo
     */
    @Transactional
    public void deletePerformance(int memberNo, int performanceNo){
        //  로그인 로직 대비
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));

        //  공연이 이미 삭제되면 예외 처리
        Performance performance = performanceRepository.findById(performanceNo).get();
        if (performance.getRemoved())
            throw new PerformanceNotFoundException("존재 하지 않는 공연입니다.");

        //  아니라면 삭제 처리
        performance.setRemoved(true);
        performanceRepository.save(performance);
    }

    /**
     * 공연 번호로 특정 공연 기본 정보 찾기
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
            List<String> imgUrlList = performanceImageService.findPerformanceImagesByPerformance(p);
            performanceResponseList.add(
                PerformanceResponse.PerformanceToResponse(p, imgUrlList)
            );
        }

        return performanceResponseList;
    }

}
