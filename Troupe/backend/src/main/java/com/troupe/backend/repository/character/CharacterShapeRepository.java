package com.troupe.backend.repository.character;

import com.troupe.backend.domain.character.CharacterShape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterShapeRepository extends JpaRepository<CharacterShape, Integer> {
}