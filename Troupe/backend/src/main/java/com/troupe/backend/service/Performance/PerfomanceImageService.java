package com.troupe.backend.service.Performance;

import com.troupe.backend.domain.performance.Performance;
import com.troupe.backend.domain.performance.PerformanceImage;
import com.troupe.backend.repository.performance.PerformanceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PerfomanceImageService {

    private final PerformanceImageRepository performanceImageRepository;

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

}
