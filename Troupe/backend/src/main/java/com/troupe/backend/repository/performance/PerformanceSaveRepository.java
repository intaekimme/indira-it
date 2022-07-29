package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSaveRepository extends JpaRepository<PerformanceSave, Integer> {

    /**
     *
     * @param member
     * @param performance
     * @return
     */
    PerformanceSave findByMemberNoAndPfNo(Member member, Performance performance);

}
