package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import com.troupe.backend.dto.converter.PerformanceConverter;
import com.troupe.backend.repository.performance.PerformanceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PerformanceImageService {

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
}
