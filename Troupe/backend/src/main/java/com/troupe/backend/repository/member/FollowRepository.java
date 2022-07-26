package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}