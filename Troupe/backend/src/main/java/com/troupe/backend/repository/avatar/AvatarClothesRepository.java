package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarClothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarClothesRepository extends JpaRepository<AvatarClothes, Integer> {
}