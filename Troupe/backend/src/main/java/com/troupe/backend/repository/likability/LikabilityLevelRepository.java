package com.troupe.backend.repository.likability;

import com.troupe.backend.domain.likability.LikabilityLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikabilityLevelRepository extends JpaRepository<LikabilityLevel, Integer> {
    /** 특정 경험치 이하의 레벨 중 가장 높은 레벨을 찾기 */
    Optional<LikabilityLevel> findTopByRequiredExpLessThanEqualOrderByLevelDesc(Integer requiredExp);

}