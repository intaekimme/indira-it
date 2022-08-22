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
     * 삭제되지 않은 공연중인 공연, 종료일 오름차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * from tb_performance as pf " +
            "where member_no = :memberNo " +
            "and is_removed = :isRemoved " +
            "and current_timestamp() between start_date and end_date " +
            "order by end_date asc ")
    Optional<Slice<Performance>> findByMemberPerforming(int memberNo, boolean isRemoved, Pageable pageable);

    /**
     * 유저가 등록한 공연 리스트
     * 삭제되지 않은 공연예정인 공연, 시작일 오름차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance as pf " +
            "where member_no = :memberNo " +
            "and pf.is_removed = :isRemoved " +
            "and current_timestamp() <= start_date " +
            "order by start_date asc ")
    Optional<Slice<Performance>> findByMemberUpcommingPerformance(int memberNo, boolean isRemoved, Pageable pageable);

    /**
     * 유저가 등록한 공연 리스트
     * 삭제되지 않은 종료된 공연, 종료일 내림차순
     * @param isRemoved
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance as pf " +
            "where member_no = :memberNo " +
            "and pf.is_removed = :isRemoved " +
            "and end_date <= current_timestamp() " +
            "order by end_date desc ")
    Optional<Slice<Performance>> findByMemberPerformanceThatHaveEnded(int memberNo, boolean isRemoved, Pageable pageable);

    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance pf join tb_member m " +
            "on pf.member_no = m.member_no " +
            "where pf.is_removed = 'b0' " +
            "and m.nickname like :keyword " +
            "and current_timestamp() between pf.start_date and pf.end_date " +
            "order by end_date asc ")
    List<Performance> findAllByNickNamePerforming(String keyword);

    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance pf join tb_member m " +
            "on pf.member_no = m.member_no " +
            "where pf.is_removed = 'b0' " +
            "and m.nickname like :keyword " +
            "and current_timestamp() <= pf.start_date " +
            "order by start_date asc ")
    List<Performance> findAllByNickNameUpcommingPerformance(String keyword);

    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance pf join tb_member m " +
            "on pf.member_no = m.member_no " +
            "where pf.is_removed = 'b0' " +
            "and m.nickname like :keyword " +
            "and end_date <= current_timestamp() " +
            "order by end_date desc ")
    List<Performance> findAllByNickNamePerformanceThatHaveEnded(String keyword);

    @Query(nativeQuery = true, value = "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.title like %:keyword% " +
            "and current_timestamp() between pf.start_date and pf.end_date) " +
            "union " +
            "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.description like %:keyword% " +
            "and current_timestamp() between pf.start_date and pf.end_date) " +
            "order by start_date asc ")
    List<Performance> findAllByTitleAndDescriptionPerforming(String keyword);

    @Query(nativeQuery = true, value = "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.title like %:keyword% " +
            "and current_timestamp() <= pf.start_date) " +
            "union " +
            "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.description like %:keyword% " +
            "and current_timestamp() <= pf.start_date) " +
            "order by start_date asc ")
    List<Performance> findAllByTitleAndDescriptionUpcommingPerformance(String keyword);

    @Query(nativeQuery = true, value = "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.title like %:keyword% " +
            "and pf.end_date <= current_timestamp()) " +
            "union " +
            "(select * from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and pf.description like %:keyword% " +
            "and pf.end_date <= current_timestamp()) " +
            "order by end_date desc ")
    List<Performance> findAllByTitleAndDescriptionPerformanceThatHaveEnded(String keyword);


    @Query(nativeQuery = true, value = "((select * , date_add(current_timestamp(), interval 9 hour) as cur_time " +
            "from tb_performance as pf " +
            "where is_removed = :isRemoved " +
            "and date_add(current_timestamp(), interval 9 hour) between start_date and end_date " +
            "order by end_date asc )" +
            "union " +
            "(select * , date_add(current_timestamp(), interval 9 hour) as cur_time " +
            "from tb_performance as pf " +
            "where is_removed = :isRemoved " +
            "and date_add(current_timestamp(), interval 9 hour) <= start_date " +
            "order by start_date asc))" +
            "union " +
            "(select * , date_add(current_timestamp(), interval 9 hour) as cur_time " +
            "from tb_performance as pf " +
            "where is_removed = :isRemoved " +
            "and end_date <= date_add(current_timestamp(), interval 9 hour) " +
            "order by end_date desc) ")
    Optional<Slice<Performance>> findAllCombined(boolean isRemoved, Pageable pageable);


    /**
     * 삭제되지 않은 공연중인 공연, 종료일 오름차순
     * @param isRemoved
     * @return
     */
    @Query(nativeQuery = true, value = "select * from tb_performance as pf " +
            "where is_removed = 'b0' " +
            "and current_timestamp() between start_date and end_date " +
            "order by end_date asc ")
    Optional<List<Performance>> findAllPerforming(boolean isRemoved);

    /**
     * 삭제되지 않은 공연예정인 공연, 시작일 오름차순
     * @param isRemoved
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
    "from tb_performance as pf " +
    "where pf.is_removed='b0' " +
    "and current_timestamp() <= start_date " +
    "order by start_date asc ")
    Optional<List<Performance>> findAllUpcommingPerformance(boolean isRemoved);

    /**
     * 삭제되지 않은 종료된 공연, 종료일 내림차순
     * @param isRemoved
     * @return
     */
    @Query(nativeQuery = true, value = "select * " +
            "from tb_performance as pf " +
            "where pf.is_removed='b0' " +
            "and end_date <= current_timestamp() " +
            "order by end_date desc ")
    Optional<List<Performance>> findAllPerformanceThatHaveEnded(boolean isRemoved);


//    =================================================================================================
    /**
     * 유저가 등록한 공연 리스트
     * @param member
     * @return
     */
    Slice<Performance> findByMemberIsRemovedOrderByCreatedTimeDesc(Member member, boolean isRemoved, Pageable pageable);

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