package com.troupe.backend.dto.Performance;

import com.troupe.backend.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestCategoryResponse {
    int category_no;
    String category_name;

    public InterestCategoryResponse(Category category) {
        this.category_no = category.getId();
        this.category_name = category.getSmallCategory();
    }
}
