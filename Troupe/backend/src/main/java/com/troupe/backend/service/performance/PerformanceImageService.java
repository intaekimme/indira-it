package com.troupe.backend.service.performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import com.troupe.backend.dto.performance.form.PerformanceModifyForm;
import com.troupe.backend.dto.converter.PerformanceConverter;
import com.troupe.backend.repository.performance.PerformanceImageRepository;
import com.troupe.backend.repository.performance.PerformanceRepository;
import com.troupe.backend.util.MyConstant;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@Service
public class PerformanceImageService {

    private final S3FileUploadService s3FileUploadService;
    private final PerformanceImageRepository performanceImageRepository;
    private final PerformanceRepository performanceRepository;

    private final PerformanceConverter performanceConverter;
    /**
     * 찾으려는 공연에 등록된 이미지 url Map 반환
     * @param performance
     * @return
     */
    @Transactional(readOnly = true)
    public Map<Integer, String> findPerformanceImagesByPerformance(Performance performance){
        List<PerformanceImage> performanceImageList = performanceImageRepository.findByPf(performance);
        Map<Integer, String> imageUrlMap = new HashMap<>();
        for(PerformanceImage p : performanceImageList){
            imageUrlMap.put(p.getId(), MyConstant.FILE_SERVER_URL + p.getImageUrl());
        }
        return imageUrlMap;
    }

    /**
     * 이미지 url들과 공연 정보를 가지고 공연 이미지 엔티티 생성 후 등록
     * @param urlList
     * @param performance
     */
    @Transactional
    public void addPerformanceImage(List<String> urlList, Performance performance) {
        List<PerformanceImage> performanceImageList = performanceConverter.toPerformanceImageEntityWhenCreate(urlList, performance);
        // 포스터 url 설정
        performance.setPosterUrl(performanceImageList.get(0).getImageUrl());
        performanceRepository.save(performance);
        performanceImageRepository.saveAll(performanceImageList);
    }

    /**
     * 공연 이미지 수정
     * @param performanceModifyForm
     * @param performance
     * @throws IOException
     */
    public void updatePerformanceImage(PerformanceModifyForm performanceModifyForm, Performance performance)
    throws IOException {
        //  삭제할 목록이 존재하면
        if(performanceModifyForm.getRemovedImages() != null){
            List<Integer> removedImages = performanceModifyForm.getRemovedImages();
            for(Integer imageNo : removedImages){
                //  S3에서 해당 이미지들 삭제하고
                PerformanceImage deleteImage = performanceImageRepository.findById(imageNo).get();
                s3FileUploadService.deleteFile(deleteImage.getImageUrl());
                //  테이블에서도 지운다.
                performanceImageRepository.deleteById(imageNo);
            }
        }
        //  추가할 이미지들이 존재하면
        if(performanceModifyForm.getNewImages() != null){
            List<MultipartFile> newImages = performanceModifyForm.getNewImages();
            //  url을 생성 후
            List<String> urlList = s3FileUploadService.upload(newImages, "performance");
            //  엔티티 변환 후 저장
            List<PerformanceImage> performanceImageList = performanceConverter.toPerformanceImageEntityWhenCreate(urlList, performance);
            performanceImageRepository.saveAll(performanceImageList);

            //  수정 후 공연에 저장된 이미지들 중 1번째를 포스터로 지정
            List<PerformanceImage> performanceImages = performanceImageRepository.findByPf(performance);
            performance.setPosterUrl(performanceImages.get(0).getImageUrl());
            performanceRepository.save(performance);
        }
    }
    public void deletePerformanceImage(Performance targetPerformance) {
        //  삭제할 공연 번호에 해당하는 이미지 모두 찾기
        List<PerformanceImage> performanceImageList = performanceImageRepository.findByPf(targetPerformance);
        //  S3에서 해당 이미지들 삭제
        for(PerformanceImage p : performanceImageList) {
            s3FileUploadService.deleteFile(p.getImageUrl());
        }

        //  해당 좌석들의 정보를 테이블에서 모두 삭제하기
        for (PerformanceImage image : performanceImageList) {
            performanceImageRepository.deleteById(image.getId());
        }
    }
}
