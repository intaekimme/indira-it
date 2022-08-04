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
    int categoryNo;
    String categoryName;

    public InterestCategoryResponse(Category category) {
        this.categoryNo = category.getId();
        this.categoryName = category.getSmallCategory();
    }
}
