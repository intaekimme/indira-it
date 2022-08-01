package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceSave;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.repository.performance.PerformanceSaveRepository;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.troupe.backend.util.MyUtil.getMemberNoFromRequestHeader;

@RequiredArgsConstructor
@Service
public class PerformanceSaveService {

    private final MemberRepository memberRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSaveRepository performanceSaveRepository;

    /**
     * 공연 북마크
     *
     * 로그인 유저인지 비로그인 유저인지 확인
     * 임시로 header로 넘어오는 memberNo로 확인 -> 토큰 정보로 변경해야함
     * memberNo 키가 없거나 값이 없으면 비로그인 유저
     * 값이 았으면 pfNo 검증
     * 키 값을 이용하거나, 값을 넘겨주되 비로그인 유저만을 구분짓는 값을 넘겨주는 방식 중 1개만 이용하면 될 것 같다.
     * @param pfNo
     * @param requestHeader
     */
    @Transactional
    public void save(int pfNo, Map<String, Object> requestHeader){
        //  키 값 이용
        if(requestHeader.get(MyConstant.MEMBER_NO) == null)
            throw new NoSuchElementException("로그인이 필요합니다.");

        //  비로그인 유저만의 값 이용
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        if(memberNo == 0)
            throw new NoSuchElementException("로그인이 필요합니다.");

        //  -----------------------------------------------------------------------------------
        //  공연 검증(있는 공연으로 클릭한거니까 없어도 될 것 같다.)
        //  공연 id가 없는 경우
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연입니다."));

        //  공연은 존재하는데 삭제된 경우
        if(performance.getRemoved())
            throw new NoSuchElementException("삭제된 공연 입니다.");

        //  -----------------------------------------------------------------------------------
        //  로그인한 유저, 존재하는 공연
        Member member = memberRepository.findById(memberNo).get();

        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        //  검색 결과가 없으면, 최초 저장 실행
        PerformanceSave found = performanceSaveRepository.findByMemberNoAndPfNo(member, performance)
                .orElse(performanceSaveRepository.save(PerformanceSave.builder()
                        .memberNo(member)
                        .pfNo(performance)
                        .createdTime(now)
                        .isRemoved(false)
                        .build()
                        )
                );

        //  검색 결과가 있으면 한 번 삭제하고 다시 저장하는 경우
        found.setRemoved(false);
        performanceSaveRepository.save(found);
    }

    /**
     * 공연 북마크 삭제
     * 유저 번호와 공연 번호를 가지고 공연 북마크 삭제 처리
     * @param pfNo
     * @param requestHeader
     */
    @Transactional
    public void delete(int pfNo, Map<String, Object> requestHeader){
        int memberNo = getMemberNoFromRequestHeader(requestHeader);
        Member member = memberRepository.findById(memberNo).get();
        Performance performance = performanceRepository.findById(pfNo).get();

        PerformanceSave found = performanceSaveRepository.findByMemberNoAndPfNo(member, performance).get();
        found.setRemoved(true);
        performanceSaveRepository.save(found);
    }

    /**
     * 유저가 저장한 공연 목록 리스트 반환
     * @param requestHeader
     * @return
     */
//    @Transactional(readOnly = true)
//    public List<PerformanceSave> list(Map<String, Object> requestHeader){
//        int memberNo = getMemberNoFromRequestHeader(requestHeader);
//        Member member = memberRepository.findById(memberNo).get();
//
//        return performanceSaveRepository.findByMemberNoAndRemovedFalse(member);
//    }

}
