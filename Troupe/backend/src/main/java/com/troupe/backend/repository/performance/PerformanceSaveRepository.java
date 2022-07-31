package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerformanceSaveRepository extends JpaRepository<PerformanceSave, Integer> {

    /**
     * 유저 번호와 공연 번호로 찾은 한 개의 공연 반환
     * @param member
     * @param performance
     * @return
     */
    Optional<PerformanceSave> findByMemberNoAndPfNo(Member member, Performance performance);

    /**
     * 유저 번호와 공연 번호로 유저가 삭제한 특정 공연 1개 반환
     * @param member
     * @param performance
     * @return
     */
    PerformanceSave findByMemberNoAndPfNoAndRemovedTrue(Member member, Performance performance);

    /**
     * 유저 번호와 공연 번호로 유저가 저장한 특정 공연 1개 반환
     * @param member
     * @param performance
     * @return
     */
    PerformanceSave findByMemberNoAndPfNoAndRemovedFalse(Member member, Performance performance);

    /**
     * 유저가 저장한 공연 목록 반환
     * @param member
     * @return
     */
    List<PerformanceSave> findByMemberNoAndRemovedFalse(Member member);

    /**
     * 유저 번호와 공연 번호로 찾은 공연 삭제
     * @param member
     * @param performance
     */
    void deleteByMemberNoAndPfNo(Member member, Performance performance);


}
