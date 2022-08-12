package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarShape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarShapeRepository extends JpaRepository<AvatarShape, Integer> {
}