package com.troupe.backend.repository.likability;

import com.troupe.backend.domain.likability.Likability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikabilityRepository extends JpaRepository<Likability, Integer> {
}