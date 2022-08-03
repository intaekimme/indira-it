package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {
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

    /**
     * 유저가 등록한 공연 리스트
     * @param member
     * @return
     */
    List<Performance> findByMemberNo(Member member);


    Performance findByMemberNoAndId(Member member, int pfNo);

    @Query(nativeQuery = true, value = "select * from tb_performance pf join tb_member m on pf.member_no = m.member_no where m.nickname like :keyword order by pf.created_time ")
    List<Performance> findByNickName(String keyword);

    @Query(nativeQuery = true, value = "(select * from tb_performance pf where pf.title like %:keyword%) " +
            "union "+
            "(select * from tb_performance pf where pf.description like %:keyword%) "+
            "order by created_time ")
    List<Performance> findByTitleAndDescription(String keyword);

}
