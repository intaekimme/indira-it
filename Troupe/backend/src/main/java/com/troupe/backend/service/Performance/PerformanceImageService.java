package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import com.troupe.backend.domain.performance.PerformancePrice;
import com.troupe.backend.dto.converter.PerformanceConverter;
import com.troupe.backend.repository.performance.PerformanceImageRepository;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PerformanceImageService {

    private final S3FileUploadService s3FileUploadService;
    private final PerformanceImageRepository performanceImageRepository;

    private final PerformanceConverter performanceConverter;
    /**
     * 찾으려는 공연에 등록된 이미지 url 리스트 반환
     * @param performance
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> findPerformanceImagesByPerformance(Performance performance){
        return performanceImageRepository.findByPf(performance).stream()
                .map(PerformanceImage::getImageUrl)
                .collect(Collectors.toList());
    }

    /**
     * 이미지 url들과 공연 정보를 가지고 공연 이미지 엔티티 생성 후 등록
     * @param urlList
     * @param performance
     */
    @Transactional
    public void addPerformanceImage(List<String> urlList, Performance performance) {
        List<PerformanceImage> performanceImageList = performanceConverter.toPerformanceImageEntityWhenCreate(urlList, performance);
        performanceImageRepository.saveAll(performanceImageList);
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
