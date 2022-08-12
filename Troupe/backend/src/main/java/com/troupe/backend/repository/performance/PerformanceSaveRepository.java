package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceSave;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PerformanceSaveRepository extends JpaRepository<PerformanceSave, Integer> {

    /**
     * 유저 번호와 공연 번호로 찾은 한 개의 공연 반환
     * @param member
     * @param performance
     * @return
     */
    Optional<PerformanceSave> findByMemberAndPf(Member member, Performance performance);

    /**
     * 유저 번호와 공연 번호로 유저가 삭제한 특정 공연 1개 반환
     * @param member
     * @param performance
     * @return
     */
    @Query("select pfs from PerformanceSave pfs " +
            "where pfs.member = :member " +
            "and pfs.pf = :performance " +
            "and pfs.isRemoved = true ")
    PerformanceSave findByMemberAndPfAndRemovedTrue(Member member, Performance performance);

    /**
     * 유저 번호와 공연 번호로 유저가 저장한 특정 공연 1개 반환
     * @param member
     * @param performance
     * @return
     */
    @Query("select pfs from PerformanceSave pfs " +
            "where pfs.member = :member " +
            "and pfs.pf = :performance " +
            "and pfs.isRemoved = false ")
    PerformanceSave findByMemberAndPfAndRemovedFalse(Member member, Performance performance);

    /**
     * 유저가 저장한 공연 목록 반환
     * @param member
     * @return
     */
    @Query("select pfs from PerformanceSave pfs " +
            "where pfs.member = :member " +
            "and pfs.isRemoved = false " +
            "order by pfs.createdTime desc ")
    Slice<PerformanceSave> findByMemberAndRemovedFalse(Member member, Pageable pageable);
    Slice<PerformanceSave> findAllByMemberAndIsRemovedOrderByCreatedTimeDesc(Member member, boolean isRemoved, Pageable pageable);
    /**
     * 유저 번호와 공연 번호로 찾은 공연 삭제
     * @param member
     * @param performance
     */
    void deleteByMemberAndPf(Member member, Performance performance);

    List<PerformanceSave> findAllByMemberAndIsRemoved(Member member, boolean isRemoved);

}
