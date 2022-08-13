package com.troupe.backend.repository.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
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
public class PerformanceReviewTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceReviewRepository performanceReviewRepository;

    @Test
    @DisplayName("공연후기 작성")
    public void saveTest() throws ParseException  {
        Member member = memberRepository.getById(3);
        Performance performance = performanceRepository.getById(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        for(int i=2; i<100; i++){
            PerformanceReview performanceReview = PerformanceReview.builder()
                    .member(member)
                    .pf(performance)
                    .createdTime(dateFormat.parse("20220804"))
                    .isModified(false)
                    .isRemoved(false)
                    .content("댓글 "+i)
                    .build();

            PerformanceReview savePerformanceReview = performanceReviewRepository.save(performanceReview);
        }


//        Assertions.assertEquals("댓글 1", savePerformanceReview.getContent());

    }

//    @Test
//    @DisplayName("공연번호에 해당하는 후기 리스트 불러오기")
//    public void findByPfNo(){
//        Performance performance = performanceRepository.findById(1).get();
//
//        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findByPf(performance);
//
//        Assertions.assertEquals(6, performanceReviewList.size());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("후기 번호로 댓글 찾기")
//    public void findById(){
//        PerformanceReview performanceReview = performanceReviewRepository.findById(1).get();
//
//        int cnt = 0;
//        for(PerformanceReview p : performanceReview.getChildrenPerformanceReview()){
//            System.out.println(p.getId()+ ", "+p.getContent());
//            cnt++;
//        }
//        System.out.println(cnt);
//        Assertions.assertEquals(1, performanceReview.getId());
//    }
//
//    @Test
//    @DisplayName("후기 번호에 해당하는 후기 내용 수정")
//    public void updateReviewNo(){
//        PerformanceReview performanceReview = performanceReviewRepository.findById(1).get();
//
//        performanceReview.setContent("댓글2");
//        performanceReviewRepository.save(performanceReview);
//
//        Assertions.assertEquals("댓글2", performanceReview.getContent());
//    }
//
//
//    @Test
//    @Transactional
//    @DisplayName("공연 번호에 해당하는 대댓글들 불러오기")
//    void findByParentPerformanceReviewTest1(){
//        Performance performance = performanceRepository.findById(1).get();
//        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findByPf(performance);
//        for(PerformanceReview performanceReview : performanceReviewList){
//            List<PerformanceReview> childrenPerformanceReview = performanceReview.getChildrenPerformanceReview();
//            System.out.println(childrenPerformanceReview.size());
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("공연 번호에 해당하는 대댓글들 불러오기")
//    void findByParentPerformanceReviewTest2(){
//        Performance performance = performanceRepository.findById(1).get();
//        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findByPf(performance);
//        for(PerformanceReview performanceReview : performanceReviewList){
//            List<PerformanceReview> childrenPerformanceReview = performanceReview.getChildrenPerformanceReview();
//            System.out.println(childrenPerformanceReview.size());
//        }
//    }
//
//    @Test
//    @DisplayName("후기 번호에 해당하는 후기만 삭제, 대댓글은 삭제 안함")
//    void 후기삭제(){
//        PerformanceReview performanceReview = performanceReviewRepository.findById(1).get();
//        performanceReview.setRemoved(true);
//        PerformanceReview savePerformanceReview = performanceReviewRepository.save(performanceReview);
//
//        Assertions.assertTrue(savePerformanceReview.getRemoved());
//    }

}
