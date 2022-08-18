package com.troupe.backend.service.performance;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformancePrice;
import com.troupe.backend.dto.converter.PerformanceConverter;
import com.troupe.backend.dto.performance.form.PerformanceForm;
import com.troupe.backend.dto.performance.form.PerformanceModifyForm;
import com.troupe.backend.dto.performance.response.PerformanceDetailResponse;
import com.troupe.backend.dto.performance.response.PerformanceResponse;
import com.troupe.backend.dto.performance.response.PriceResponse;
import com.troupe.backend.dto.performance.response.ProfilePfResponse;
import com.troupe.backend.exception.member.MemberNotFoundException;
import com.troupe.backend.exception.performance.PerformanceNotFoundException;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.util.MyConstant;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class PerformanceService {

    private final PerformanceImageService performanceImageService;
    private final PerformancePriceService performancePriceService;
    private final S3FileUploadService s3FileUploadService;
    private final PerformanceRepository performanceRepository;
    private final MemberRepository memberRepository;

    private final PerformanceConverter performanceConverter;


    @Transactional
    public void register(int member,
                         PerformanceForm performanceform) throws IOException{
        //  공연 기본 정보 등록 서비스 호출
        Performance performance = addPerformance(member, performanceform);
        //  S3 공연 이미지 업로드 서비스 호출
        if(!performanceform.getImages().isEmpty()){
            List<String> urlList = s3FileUploadService.upload(performanceform.getImages(), "performance");
            //  공연 이미지 정보 등록 서비스 호출
            performanceImageService.addPerformanceImage(urlList, performance);
        }
        //  공연 좌석 정보 등록 서비스 호출
        if(!performanceform.getSeatPrice().isEmpty())
            performancePriceService.addPerformancePrice(performanceform, performance);
    }

    @Transactional
    public void modify(int memberNo, int pfNo, PerformanceModifyForm performanceModifyForm) throws Exception{
        //  공연 기본 정보 수정 서비스 호출
        Performance performance = updatePerformance(memberNo, pfNo, performanceModifyForm);
        //  공연 이미지 정보 수정 서비스 호출
        performanceImageService.updatePerformanceImage(performanceModifyForm, performance);
        //  공연 좌석 정보 수정 서비스 호출
        performancePriceService.updatePerformancePrice(performanceModifyForm, performance);
    }

    @Transactional
    public void delete(int memberNo, int pfNo){
        //  공연 기본 정보 삭제 서비스 호출
        Performance deletedPerformance = deletePerformance(memberNo, pfNo);
        //  기본적인 예외처리 통과함, 공연 번호로 바로

        //  공연 이미지 정보 삭제 서비스 호출
        performanceImageService.deletePerformanceImage(deletedPerformance);
        //  공연 좌석 정보 삭제 서비스 호출
        performancePriceService.deletePerformancePrice(deletedPerformance);
    }

    /**
     * 공연 기본 정보 등록 서비스
     * @param memberNo
     * @param performanceform
     * @return
     */
    @Transactional
    public Performance addPerformance(int memberNo, PerformanceForm performanceform){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("존재 하지 않는 유저입니다."));
        if(performanceform.getTitle() == null || performanceform.getTitle().isBlank())
            throw new RuntimeException("공연 제목을 입력하세요");

        Performance newPerformance = performanceConverter.toPerformanceEntityWhenCreate(performanceform, member);
        return performanceRepository.save(newPerformance);
    }

    /**
     * 공연 기본 정보 수정 서비스
     * @param memberNo
     * @param performanceNo
     * @param performanceModifyForm
     * @return
     */
    @Transactional
    public Performance updatePerformance(int memberNo, int performanceNo, PerformanceModifyForm performanceModifyForm){
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        if(!member.equals(performance.getMember()))
            throw new NoSuchElementException("수정 권한이 없는 공연입니다.");
        //  기존 정보 수정
        Performance updatePerformance = performanceConverter.toPerformanceEntityWhenUpdate(performanceModifyForm, member, performance);
        return performanceRepository.save(updatePerformance);
    }

    /**
     * 공연 기본 정보 삭제 서비스
     * @param memberNo
     * @param performanceNo
     */
    @Transactional
    public Performance deletePerformance(int memberNo, int performanceNo){

        //  로그인 로직 대비, 인증되지 않은 유저는 삭제하면 안됨.
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("존재 하지 않는 유저입니다."));

        //  요청으로 보낸 공연번호로 찾은 공연의 유저 정보와 헤더로 넘어온 로그인 유저 정보가 다르면 삭제하면 안됨.
        Performance found = performanceRepository.findById(performanceNo).get();
        if(!found.getMember().equals(member))
            throw new NoSuchElementException("존재 하지 않는 공연입니다.");

        //  공연이 이미 삭제되면 예외 처리
        if (found.isRemoved())
            throw new PerformanceNotFoundException("존재 하지 않는 공연입니다.");

        //  아니라면 삭제 처리
        found.setRemoved(true);
        performanceRepository.save(found);
        return found;
    }

    /**
     * 공연 번호로 특정 공연 기본 정보 찾기
     * @param performanceNo
     * @return
     */
    @Transactional(readOnly = true)
    public Performance findPerformanceByNo(int performanceNo){
        Performance performance = performanceRepository.findById(performanceNo)
                .orElseThrow(() -> new PerformanceNotFoundException("존재 하지 않는 공연입니다."));
        return performance;
    }

    /**
     * 공연 목록 조회, 최근 등록 순으로 정렬
     * @return
     */
    @Transactional(readOnly = true)
    public List<PerformanceResponse> findAll(Pageable pageable){
        List<Performance> combinedList = new ArrayList<>();

        //  진행중
        Slice<Performance> performingList =
                performanceRepository.findAllPerforming(false, pageable).get();
        for(Performance p : performingList)
            combinedList.add(p);

        //  진행 예정
        Slice<Performance> upcommingList =
                performanceRepository.findAllUpcommingPerformance(false, pageable).get();
        for (Performance p : upcommingList)
            combinedList.add(p);

        //  종료
        Slice<Performance> endList =
                performanceRepository.findAllPerformanceThatHaveEnded(false,pageable).get();
        for (Performance p : endList)
            combinedList.add(p);


        List<PerformanceResponse> performanceResponseList = new ArrayList<>();

        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        for(Performance p : combinedList){
            //  공연 상태 계산
            StringBuilder sb = new StringBuilder();
            if(now.before(p.getStartDate())) sb.append(MyConstant.PREV);
            else if(now.after(p.getStartDate()) && now.before(p.getEndDate())) sb.append(MyConstant.ING);
            else if(now.after(p.getEndDate())) sb.append(MyConstant.END);

            log.info(p.toString());
            Map<Integer, String> imgUrlList = performanceImageService.findPerformanceImagesByPerformance(p);
            performanceResponseList.add(
                    PerformanceResponse.builder()
                            .pfNo(p.getId())
                            .title(p.getTitle())
                            .description(p.getDescription())
                            .image(imgUrlList)
                            .location(p.getLocation())
                            .detailTime(p.getDetailTime())
                            .category(p.getCategory().getSmallCategory())
                            .status(sb.toString())
                            .runtime(p.getRuntime())
                            .startDate(p.getStartDate().toString())
                            .endDate(p.getEndDate().toString())
                            .build()
            );
        }

        return performanceResponseList;
    }

    /**
     * 검색 키워드로 검색
     * @param condition
     * @param keyword
     * @return
     */
    public List<PerformanceResponse> findQueryAll(String condition, String keyword) {
        condition.trim(); keyword.trim();
        if(keyword.isBlank())
            throw new NoSuchElementException("검색어가 없습니다.");

        List<Performance> performanceList = null;
        // 검색 조건과 질의어로 질의
        if(condition.equals("nickname")){       //  작성자
            performanceList = performanceRepository.findAllByNickName(keyword);
        }else if(condition.equals("title")){    //  제목 + 내용
            performanceList = performanceRepository.findAllByTitleAndDescription(keyword);
        }

        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        List<PerformanceResponse> performanceResponseList = new ArrayList<>();
        for(Performance p : performanceList){
            //  공연 상태 계산
            StringBuilder sb = new StringBuilder();
            if(now.before(p.getStartDate())) sb.append(MyConstant.PREV);
            else if(now.after(p.getStartDate()) && now.before(p.getEndDate())) sb.append(MyConstant.ING);
            else if(now.after(p.getEndDate())) sb.append(MyConstant.END);

            Map<Integer, String> imgUrlList = performanceImageService.findPerformanceImagesByPerformance(p);
            performanceResponseList.add(
                    PerformanceResponse.builder()
                            .pfNo(p.getId())
                            .title(p.getTitle())
                            .description(p.getDescription())
                            .image(imgUrlList)
                            .location(p.getLocation())
                            .detailTime(p.getDetailTime())
                            .category(p.getCategory().getSmallCategory())
                            .status(sb.toString())
                            .runtime(p.getRuntime())
                            .startDate(p.getStartDate().toString())
                            .endDate(p.getEndDate().toString())
                            .build()
            );
        }

        return performanceResponseList;

    }
    /**
     * 공연 상세 정보
     * 공연 번호로 요청이 들어오면
     * 해당 공연 기본 정보와 이미지 저장 url을 받아 반환
     * @param pfNo
     * @return
     */
    @Transactional(readOnly = true)
    public PerformanceDetailResponse detail(int pfNo) {
        //  등록되지 않은 공연 조회
        Performance performance = performanceRepository.findById(pfNo)
                .orElseThrow(() -> new NoSuchElementException("존재 하지 않는 공연입니다."));

        //  삭제된 공연 조회
        if(performance.isRemoved())
            throw new NoSuchElementException("삭제된 공연 입니다");

        //  공연을 찾으면, url을 찾고
        Map<Integer, String> urlList = performanceImageService.findPerformanceImagesByPerformance(performance);

        //  공연을 찾으면, 좌석을 찾고
        List<PerformancePrice> priceList = performancePriceService.findAllPrice(performance);
        List<PriceResponse> priceResponseList = new ArrayList<PriceResponse>();
        for(PerformancePrice price : priceList){
            priceResponseList.add(PriceResponse.builder()
                    .id(price.getId())
                    .seat(price.getSeat())
                    .price(price.getPrice())
                    .build());
        }
        // 공연 상태 계산
        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        //  공연 상태 계산
        StringBuilder sb = new StringBuilder();
        if(now.before(performance.getStartDate())) sb.append(MyConstant.PREV);
        else if(now.after(performance.getStartDate()) && now.before(performance.getEndDate())) sb.append(MyConstant.ING);
        else if(now.after(performance.getEndDate())) sb.append(MyConstant.END);

        //  응답 폼으로 만들어 반환
        return PerformanceDetailResponse.builder()
                .pfNo(pfNo)
                .imageUrl(urlList)
                .price(priceResponseList)
                .memberNo(performance.getMember().getMemberNo())
                .profileImg(MyConstant.FILE_SERVER_URL + performance.getMember().getProfileImageUrl())
                .nickname(performance.getMember().getNickname())
                .title(performance.getTitle())
                .location(performance.getLocation())
                .runtime(performance.getRuntime())
                .description(performance.getDescription())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .createdTime(performance.getCreatedTime())
                .updatedTime(performance.getUpdatedTime())
                .category(performance.getCategory().getSmallCategory())
                .status(sb.toString())
                .detailTime(performance.getDetailTime())
                .isRemoved(performance.isRemoved())
                .build();
    }

//  =================================================================================
//  profile service
//  =================================================================================

    /**
     * 프로필 유저가 등록한 공연 목록 불러오기
     * @param memberNo
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProfilePfResponse> findRegisteredList(int memberNo, Pageable pageable) {
        Member member = memberRepository.findById(memberNo).get();
        Slice<Performance> performanceList = performanceRepository.findByMemberIsRemovedOrderByCreatedTimeDesc(member, false, pageable);

        List<ProfilePfResponse> profilePfResponseList = new ArrayList<>();

        // 공연 상태 계산
        // 1. LocalDateTime 객체 생성(현재 시간)
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2. LocalDateTime -> Date 변환
        Date now = java.sql.Timestamp.valueOf(localDateTime);

        for(Performance p : performanceList){
            //  공연 상태 계산
            StringBuilder sb = new StringBuilder();
            if(now.before(p.getStartDate())) sb.append(MyConstant.PREV);
            else if(now.after(p.getStartDate()) && now.before(p.getEndDate())) sb.append(MyConstant.ING);
            else if(now.after(p.getEndDate())) sb.append(MyConstant.END);

            Map<Integer, String> images = performanceImageService.findPerformanceImagesByPerformance(p);

            profilePfResponseList.add(ProfilePfResponse.builder()
                    .pfNo(p.getId())
                    .memberNo(member.getMemberNo())
                    .nickname(member.getNickname())
                    .title(p.getTitle())
                    .description(p.getDescription())
                    .images(images)
                    .poster(p.getPosterUrl())
                    .category(p.getCategory().getSmallCategory())
                    .status(sb.toString())
                    .runtime(p.getRuntime())
                    .startDate(p.getStartDate())
                    .endDate(p.getEndDate())
                    .build()
            );
        }
        return profilePfResponseList;
    }


}