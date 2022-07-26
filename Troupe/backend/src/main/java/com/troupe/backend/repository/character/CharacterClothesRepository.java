package com.troupe.backend.repository.character;

import com.troupe.backend.domain.character.CharacterClothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterClothesRepository extends JpaRepository<CharacterClothes, Integer> {
}