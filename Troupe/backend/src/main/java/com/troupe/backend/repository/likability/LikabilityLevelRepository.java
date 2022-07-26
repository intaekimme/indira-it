package com.troupe.backend.repository.likability;

import com.troupe.backend.domain.likability.LikabilityLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikabilityLevelRepository extends JpaRepository<LikabilityLevel, Integer> {
}