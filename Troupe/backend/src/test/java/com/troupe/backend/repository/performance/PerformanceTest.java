package com.troupe.backend.repository.performance;


import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformanceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PerformanceRepository performanceRepository;

    @Test
    @DisplayName("공연 엔티티 및 save 메소드 테스트")
    public void saveTest() throws ParseException {

        Member member = memberRepository.getById(3);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Performance performance = Performance.builder()
                .memberNo(member)
                .title("새로운 연극")
                .location("새로운 극장")
                .runtime(120)
                .createdTime(dateFormat.parse("20220709"))
                .updatedTime(dateFormat.parse("20220925"))
                .posterUrl("http://www.def.ghi")
                .codeNo(1)
                .detailTime("15:30")
                .description("test test test test test test test test test test test test test test")
                .isRemoved(false)
                .build();

        Performance savePerformance = performanceRepository.save(performance);

//        Assertions.assertEquals("인천광역시 부평구 부개동", savePerformance.getLocation());
//        Assertions.assertEquals("공연1", savePerformance.getTitle());

    }   //  end of saveTest


    @Test
    @DisplayName("공연 수정 테스트")
    public void updateTest() throws ParseException {
        Performance targetPerformance = performanceRepository.findById(14).get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        targetPerformance.setDescription("test test test");
        targetPerformance.setCreatedTime(dateFormat.parse("20220721"));

        performanceRepository.save(targetPerformance);

        Assertions.assertEquals("공연 4", targetPerformance.getTitle());
    }

    @Test
    @DisplayName("공연 리스트 불러오기")
    public void findAllTest(){
        List<Performance> performanceList = performanceRepository.findAll();

        Assertions.assertTrue(performanceList.size() > 0);
    }

    @Test
    @DisplayName("공연 삭제")
    public void deleteTest(){
        Performance performance = performanceRepository.findById(15).get();

        performance.setRemoved(true);
        performanceRepository.save(performance);

        Assertions.assertEquals(true, performance.getRemoved());
    }

    @Test
    @DisplayName("공연 제목과 일치하는 공연 찾기")
    public void findByTitleTest(){
        Performance performance = performanceRepository.findByTitleLike("더블데이트 [부산]");

        Assertions.assertEquals("감천문화마을", performance.getLocation().trim());
    }

    @Test
    @DisplayName("공연 제목을 포함하는 공연 찾기")
    public void findByTitleContainingTest() {
        List<Performance> performanceList = performanceRepository.findByTitleContaining("공연");

        System.out.println(performanceList.size());
        Assertions.assertTrue(performanceList.size() > 0);
    }


}
