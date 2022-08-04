package com.troupe.backend.repository.category;


import com.troupe.backend.domain.category.Category;
import com.troupe.backend.domain.category.InterestCategory;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InterestCategoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    InterestCategoryRepository interestCategoryRepository;

    @Test
    public void saveTest(){
        Member member = memberRepository.getById(3);

        List<Category> categoryList = categoryRepository.findByBigCategory("연극");

        for(Category category : categoryList) {
            InterestCategory interestCategory = InterestCategory.builder()
                    .memberNo(member)
                    .category(category)
                    .build();

            InterestCategory saveInterestCategory = interestCategoryRepository.save(interestCategory);
        }

    }
}
