package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.dto.Performance.Performanceform;
import com.troupe.backend.exception.MemberNotFoundException;
import com.troupe.backend.exception.performance.PerformanceNotFoundException;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final MemberRepository memberRepository;


    /**
     * 공연 등록 서비스
     * @param memberNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance addPerformance(int memberNo, Performanceform performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
       return performanceRepository.save(performanceform.createEntity(member));
    }

    /**
     * 공연 삭제 서비스
     * @param memberNo
     * @param performanceNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance updatePerformance(int memberNo, int performanceNo, Performanceform performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        return performanceRepository.save(performanceform.updateEntity(member, performance));
    }

    @Transactional
    public void deletePerformance(int memberNo, int performanceNo){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        performance.setRemoved(true);
    }

}
