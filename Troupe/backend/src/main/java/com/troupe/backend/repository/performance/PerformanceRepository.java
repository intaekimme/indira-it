package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {
    /**
     * 유저가 등록한 공연 리스트
     * @param member
     * @return
     */
    Slice<Performance> findByMemberIsRemovedOrderByCreatedTimeDesc(Member member, boolean isRemoved, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from tb_performance pf join tb_member m on pf.member_no = m.member_no where m.nickname like :keyword order by pf.created_time ")
    List<Performance> findAllByNickName(String keyword);

    @Query(nativeQuery = true, value = "(select * from tb_performance pf where pf.title like %:keyword%) " +
            "union "+
            "(select * from tb_performance pf where pf.description like %:keyword%) "+
            "order by created_time ")
    List<Performance> findAllByTitleAndDescription(String keyword);

    /**
     * 삭제되지 않은 공연중인 공연, 종료일 오름차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * from tb_performance as pf " +
            "where is_removed = 'b0' " +
            "and current_timestamp() between start_date and end_date " +
            "order by end_date asc ")
    Optional<Slice<Performance>> findAllPerforming(boolean isRemoved, Pageable pageable);

    /**
     * 삭제되지 않은 공연예정인 공연, 시작일 오름차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
    "from troupe.tb_performance as pf " +
    "where pf.is_removed='b0' " +
    "and current_timestamp() <= start_date " +
    "order by start_date asc ")
    Optional<Slice<Performance>> findAllUpcommingPerformance(boolean isRemoved, Pageable pageable);

    /**
     * 삭제되지 않은 종료된 공연, 종료일 내림차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
            "from troupe.tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and end_date <= current_timestamp() " +
            "order by end_date desc ")
    Optional<Slice<Performance>> findAllPerformanceThatHaveEnded(boolean isRemoved, Pageable pageable);


//    =================================================================================================
    /**
     * 제목에 매개변수를 포함한 공연 검색 %like%
     * @param title
     * @return List<Performance>
     */
    List<Performance> findByTitleContaining(String title);

    /**
     * 제목과 일치한 공연 검색
     * @param title
     * @return Performance
     */
    Performance findByTitleLike(String title);

//    Optional<Slice<Performance>> findAllByIsRemovedOrderByCreatedTimeDesc(boolean isRemoved, Pageable pageable);
    Slice<Performance> findByMemberOrderByCreatedTimeDesc(Member member, Pageable pageable);
    List<Performance> findAllByMember(Member member);
    Optional<Performance> findByMemberAndId(Member member, int pfNo);
}
