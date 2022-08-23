package com.troupe.backend.repository.performance;


import com.troupe.backend.domain.category.Category;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.repository.category.CategoryRepository;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PerformanceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    CategoryRepository categoryRepository;

//    @Test
//    @DisplayName("공연 엔티티 및 save 메소드 테스트")
//    public void saveTest() throws ParseException {
//
//        Member member = memberRepository.getById(3);
//
//        Category category = categoryRepository.findById(1).get();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        Performance performance = Performance.builder()
//                .member(member)
//                .title("새로운 연극")
//                .location("새로운 극장")
//                .runtime(120)
//                .createdTime(dateFormat.parse("20220709"))
//                .updatedTime(dateFormat.parse("20220925"))
//                .posterUrl("http://www.def.ghi")
//                .category(category)
//                .detailTime("15:30")
//                .description("test test test test test test test test test test test test test test")
//                .isRemoved(false)
//                .build();
//
//        Performance savePerformance = performanceRepository.save(performance);
//
////        Assertions.assertEquals("인천광역시 부평구 부개동", savePerformance.getLocation());
////        Assertions.assertEquals("공연1", savePerformance.getTitle());
//
//    }   //  end of saveTest
//
//
//    @Test
//    @DisplayName("공연 수정 테스트")
//    public void updateTest() throws ParseException {
//        Performance targetPerformance = performanceRepository.findById(13).get();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//
//        targetPerformance.setDescription("test test test");
//        targetPerformance.setCreatedTime(dateFormat.parse("20220721"));
//
//        performanceRepository.save(targetPerformance);
//
//        Assertions.assertEquals("공연2", targetPerformance.getTitle());
//    }
//
    @Test
    @DisplayName("공연 리스트 불러오기")
    public void findAllTest(){
        int pageNumber = 0;
        for(int j=0; j<10; j++){
            pageNumber = j;

            List<Performance> combinedList = new ArrayList<>();

            //  진행중
            List<Performance> performingList =
                    performanceRepository.findAllPerforming(false).get();
            for(Performance p : performingList)
                combinedList.add(p);

            //  진행 예정
            List<Performance> upcommingList =
                    performanceRepository.findAllUpcommingPerformance(false).get();
            for (Performance p : upcommingList)
                combinedList.add(p);

            //  종료
            List<Performance> endList =
                    performanceRepository.findAllPerformanceThatHaveEnded(false).get();
            for (Performance p : endList)
                combinedList.add(p);

            //  combined list paging
            int startNumber = pageNumber * 6;
            int endNumber = (pageNumber + 1) * 6 - 1;

            List<Performance> pagingList = new ArrayList<>();
            for(int i = startNumber; i <= endNumber; i++){
                if(i >= combinedList.size()-1) break;
                Performance p = combinedList.get(i);
                System.out.print(p.getId()+" "+p.getTitle()+", ");
                pagingList.add(combinedList.get(i));
            }
            System.out.println();
        }
    }

    @Test
    @DisplayName("유저가 등록한 공연 리스트 불러오기")
    public void findAllByMemberTest(){
        int pageNumber = 0;
        for(int j=0; j<=3; j++){
            pageNumber = j;

            List<Performance> combinedList = new ArrayList<>();

            //  진행중
            List<Performance> performingList =
                    performanceRepository.findByMemberPerforming(1822,false).get();
            for(Performance p : performingList)
                combinedList.add(p);

            //  진행 예정
            List<Performance> upcommingList =
                    performanceRepository.findByMemberUpcommingPerformance(1822,false).get();
            for (Performance p : upcommingList){
                System.out.println(p.getId()+" "+p.getTitle());
                combinedList.add(p);
            }

            //  종료
            List<Performance> endList =
                    performanceRepository.findByMemberPerformanceThatHaveEnded(1822,false).get();
            for (Performance p : endList)
                combinedList.add(p);

            //  combined list paging
            int startNumber = pageNumber * 6;
            int endNumber = (pageNumber + 1) * 6 - 1;
            System.out.println("==================================");
            System.out.println(combinedList.size());
            System.out.println("startNumber: "+startNumber+" endNumber: "+endNumber);
//            for (Performance p : combinedList)
//                System.out.println(p.getId() + " " + p.getTitle());
            System.out.println("==================================");
            List<Performance> pagingList = new ArrayList<>();
            for(int i = startNumber; i <= endNumber; i++){
                if(i > combinedList.size()-1) break;
                Performance p = combinedList.get(i);
                System.out.print("i: "+ i +" "+p.getId()+" "+p.getTitle()+", ");
                pagingList.add(combinedList.get(i));
            }
            System.out.println();
        }
    }
//
//    @Test
//    @DisplayName("공연 삭제")
//    public void deleteTest(){
//        Performance performance = performanceRepository.findById(15).get();
//
//        performance.setRemoved(true);
//        performanceRepository.save(performance);
//
//        Assertions.assertTrue(performance.isRemoved());
//    }
//
//    @Test
//    @DisplayName("공연 제목과 일치하는 공연 찾기")
//    public void findByTitleTest(){
//        Performance performance = performanceRepository.findByTitleLike("더블데이트 [부산]");
//
//        Assertions.assertEquals("감천문화마을", performance.getLocation().trim());
//    }
//
//    @Test
//    @DisplayName("공연 제목을 포함하는 공연 찾기")
//    public void findByTitleContainingTest() {
//        List<Performance> performanceList = performanceRepository.findByTitleContaining("공연");
//
//        System.out.println(performanceList.size());
//        Assertions.assertTrue(performanceList.size() > 0);
//    }
//
//    @Test
//    @DisplayName("유저 id로 공연 찾기")
//    public void findByMemberNo(){
//        Member member = memberRepository.getById(3);
//
//        List<Performance> performanceList = performanceRepository.findAllByMember(member);
//
//        for (Performance pf : performanceList){
//            System.out.println(pf.getTitle());
//        }
//        Assertions.assertTrue(performanceList.size() > 0);
//    }
//
//    @Test
//    @DisplayName("공연 목록 조회, 생성 시간 순 정렬")
//    public void 공연_목록_조회_생성_시간_순_정렬(){
//        List<Performance> performanceList = performanceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
//
//        for(Performance p : performanceList){
//            System.out.println(p.getId() +" "+p.getTitle()+" "+p.getCreatedTime());
//        }
//    }

}
