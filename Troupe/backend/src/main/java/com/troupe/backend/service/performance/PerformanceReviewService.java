package com.troupe.backend.service.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceReview;
import com.troupe.backend.dto.performance.form.PfReviewForm;
import com.troupe.backend.dto.performance.response.PfReviewResponse;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.repository.performance.PerformanceReviewRepository;
import com.troupe.backend.service.member.LikabilityService;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PerformanceReviewService {

    private final MemberRepository memberRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceReviewRepository performanceReviewRepository;

    private final LikabilityService likabilityService;

    /**
     * 공연후기 작성
     * @param request
     */
    @Transactional
    public PfReviewResponse add(PfReviewForm request){

        //  비로그인 유저만의 값 이용
        if(request.getMemberNo() == 0){
            log.info("유효 하지 않은 값");
            throw new NoSuchElementException("로그인이 필요합니다.");
        }
        Member member = memberRepository.findById(request.getMemberNo()).get();
        Performance performance = performanceRepository.findById(request.getPfNo()).get();

        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);
        PerformanceReview performanceReview = null;
        if(request.getParentReviewNo() != 0){
            PerformanceReview parentReview = performanceReviewRepository.findById(request.getParentReviewNo()).get();
            performanceReview = PerformanceReview.builder()
                    .member(member)
                    .pf(performance)
                    .createdTime(now)
                    .isModified(false)
                    .isRemoved(false)
                    .content(request.getContent())
                    .parentPerformanceReview(parentReview)
                    .build();
        } else{
            performanceReview = PerformanceReview.builder()
                    .member(member)
                    .pf(performance)
                    .createdTime(now)
                    .isModified(false)
                    .isRemoved(false)
                    .content(request.getContent())
                    .build();
        }
        PfReviewResponse response = null;
        try {
            PerformanceReview savedReview = performanceReviewRepository.save(performanceReview);


            int starMemberNo = savedReview.getPf().getMember().getMemberNo();
            int fanMemberNo = savedReview.getMember().getMemberNo();
            likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_PERFORMANCE_REVIEW);

            response = PfReviewResponse.builder()
                    .reviewNo(savedReview.getId())
                    .pfNo(performance.getId())
                    .memberNo(member.getMemberNo())
                    .nickname(member.getNickname())
                    .profileImageUrl(MyConstant.FILE_SERVER_URL + member.getProfileImageUrl())
                    .comment(request.getContent())
                    .isRemoved(savedReview.getRemoved())
                    .isModified(savedReview.isModified())
                    .createdTime(savedReview.getCreatedTime())
                    .childComments(new ArrayList<>())   //  첫 원댓글이므로 대댓글이 존재하지 않음
                    .build();
        }catch (Exception e){

        }
        return response;
    }

    /**
     * 공연 후기 삭제
     * @param pfNo
     * @param reviewNo
     */
    @Transactional
    public void delete(int pfNo, int reviewNo) {
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연입니다."));
        //  refactoring : 리뷰 번호로만 삭제해도 되지않을까?
        PerformanceReview performanceReview = performanceReviewRepository.findByPfAndId(performance, reviewNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));
        performanceReview.setRemoved(true);
        performanceReviewRepository.save(performanceReview);

        int starMemberNo = performanceReview.getPf().getMember().getMemberNo();
        int fanMemberNo = performanceReview.getMember().getMemberNo();
        likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_PERFORMANCE_REVIEW);
    }

    /**
     * 공연 후기 리스트 반환(대댓글 없음)
     * @param pfNo
     * @return
     */
    @Transactional(readOnly = true)
    public List<PfReviewResponse> findPfReviewList(int pfNo){
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연입니다."));

        //  공연 번호로 찾은 원댓글들
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findByPfAndParentPerformanceReviewIsNull(performance)
                .orElse(new ArrayList<>());
        //  원 댓글 응답을 담은 배열
        List<PfReviewResponse> pfReviewResponseList = new ArrayList<>();

        //  찾은 원댓글을 순회하며
        for (PerformanceReview review : performanceReviewList){
            Member fromMember = review.getMember();

            //  원 댓글에 달려있는 대댓글 리뷰들
            List<PerformanceReview> childReviewList = review.getChildrenPerformanceReview();
            //  원 댓글 응답에 추가할 대댓글 응답 배열
            List<PfReviewResponse> childReviewResponseList = new ArrayList<>();

            //  대댓글이 없으면 대댓글 응답을 만들 필요가 없음
            if(!childReviewList.isEmpty()){
                for(PerformanceReview children : childReviewList){
                    Member toMember = review.getMember();   //  대댓글 작성자
                    PfReviewResponse childrenItem = PfReviewResponse.builder()
                            .reviewNo(children.getId())
                            .pfNo(performance.getId())
                            .memberNo(toMember.getMemberNo())
                            .nickname(toMember.getNickname())
                            .profileImageUrl(MyConstant.FILE_SERVER_URL + toMember.getProfileImageUrl())
                            .comment(children.getContent())
                            .isRemoved(children.getRemoved())
                            .isModified(children.isModified())
                            .createdTime(children.getCreatedTime())
                            .parentCommentNo(review.getId())    //  부모 댓글 번호는 순회하는 댓글의 id
                            .childComments(new ArrayList<>())   //  depth가 2이므로 하위 대댓글이 존재하지 않음
                            .build();
                    //  대댓글 응답에 추가
                    childReviewResponseList.add(childrenItem);
                }
            }

            //  댓글 응답
            PfReviewResponse item = PfReviewResponse.builder()
                    .reviewNo(review.getId())
                    .pfNo(performance.getId())
                    .memberNo(fromMember.getMemberNo())
                    .nickname(fromMember.getNickname())
                    .profileImageUrl(MyConstant.FILE_SERVER_URL + fromMember.getProfileImageUrl())
                    .comment(review.getContent())
                    .isRemoved(review.getRemoved())
                    .isModified(review.isModified())
                    .createdTime(review.getCreatedTime())
                    .parentCommentNo(0)     //  원댓글은 부모가 존재하지 않으므로 부모 댓글 번호 0으로 설정
                    .childComments(childReviewResponseList)
                    .build();

//            if(review.getParentPerformanceReview() != null) item.setParentCommentNo(review.getParentPerformanceReview().getId());
//            else item.setParentCommentNo(0);

            pfReviewResponseList.add(item);
        }
        return pfReviewResponseList;
    }


    /**
     * 공연후기수정, 수정 요청 당시에는 공연 존재, 요청 이 후 삭제된 것은 논외
     * @param pfNo
     * @param reviewNo
     * @param content
     */
    @Transactional
    public PfReviewResponse modify(int pfNo, int reviewNo, String content) {
        //  공연 엔티티를 하나 불러오고
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공연 입니다."));
        //  리뷰테이블에서 공연 엔티티와 id: reviewNo로 리뷰 엔티티를 찾고
        PerformanceReview performanceReview = performanceReviewRepository.findByPfAndId(performance, reviewNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 리뷰 입니다."));
        //  is_modified 값과, content 값만 변경해서 set 해주고, 나머지는 그대로
        performanceReview.setModified(true);
        performanceReview.setContent(content);
        //  save
        PerformanceReview result = performanceReviewRepository.save(performanceReview);

        PfReviewResponse response = PfReviewResponse.builder()
                .reviewNo(result.getId())
                .pfNo(performance.getId())
                .memberNo(result.getMember().getMemberNo())
                .nickname(result.getMember().getNickname())
                .profileImageUrl(MyConstant.FILE_SERVER_URL + result.getMember().getProfileImageUrl())
                .comment(result.getContent())
                .isRemoved(result.getRemoved())
                .isModified(result.isModified())
                .createdTime(result.getCreatedTime())
                .build();

        if(result.getParentPerformanceReview() != null) {
            response.setParentCommentNo(result.getParentPerformanceReview().getId());
        }

        return response;
    }


    /**
     * 공연후기대댓글
     * @param pfNo
     * @param reviewNo
     * @return
     */
    @Transactional(readOnly = true)
    public List<PfReviewResponse> findPfChildReviewList(int pfNo, int reviewNo) {
        //  공연 엔티티를 하나 불러오고
        Performance performance = performanceRepository.findById(pfNo).get();
        //  리뷰테이블에서 공연 엔티티와 id: reviewNo로 리뷰 엔티티를 찾고
        PerformanceReview performanceReview = performanceReviewRepository.findByPfAndId(performance, reviewNo).get();
        //  리뷰 엔티티에서 대댓글 리스트를 불러오고
        List<PerformanceReview> childReviewList = performanceReview.getChildrenPerformanceReview();

        //  리뷰 하나를 반환 폼으로 만듦
        List<PfReviewResponse> pfReviewResponseList = new ArrayList<>();
        for (PerformanceReview review : childReviewList){
            Member member = review.getMember();
            PfReviewResponse item = PfReviewResponse.builder()
                    .reviewNo(review.getId())
                    .pfNo(performance.getId())
                    .memberNo(member.getMemberNo())
                    .nickname(member.getNickname())
                    .profileImageUrl(MyConstant.FILE_SERVER_URL + member.getProfileImageUrl())
                    .comment(review.getContent())
                    .isRemoved(review.getRemoved())
                    .isModified(review.isModified())
                    .createdTime(review.getCreatedTime())
                    .build();

            if(review.getParentPerformanceReview() != null) item.setParentCommentNo(review.getParentPerformanceReview().getId());
            else item.setParentCommentNo(0);

            pfReviewResponseList.add(item);
        }

        return pfReviewResponseList;
    }
}