package com.troupe.backend.repository.category;

import com.troupe.backend.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Integer> {
    List<Category> findByBigCategory(String bigCategory);
    Optional<Category> findBySmallCategory(String smallCategory);
    Category findByCodeName(String codeName);
}
