package com.troupe.backend.repository.category;


import com.troupe.backend.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryTest {
    @Autowired
    CategoryRepository categoryRepository;

//    @Test
//    public void saveTest() {
//        Category category = Category.builder()
//                .bigCategory("복합")
//                .smallCategory("복합")
//                .codeName("EEEA")
//                .build();
//
//        Category saveCategory = categoryRepository.save(category);
//
////        Assertions.assertEquals("연극", saveCategory.getBigCategory());
////        Assertions.assertEquals("AAAA", saveCategory.getCodeName());
//
//
//    }


}
