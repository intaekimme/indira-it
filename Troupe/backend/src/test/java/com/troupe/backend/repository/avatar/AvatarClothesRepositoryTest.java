package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarClothes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarClothesRepositoryTest {
    @Autowired
    AvatarClothesRepository ccr;

    @Test
    public void saveAndFindTest () {
        AvatarClothes clothes1 = ccr.save(AvatarClothes.builder().clothesNo(1).clothesUrl("url1").build());
        AvatarClothes clothes2 = ccr.save(AvatarClothes.builder().clothesNo(2).clothesUrl("url2").build());
        AvatarClothes clothes3 = AvatarClothes.builder().clothesNo(3).clothesUrl("url3").build();

        assertThat(ccr.findById(1).get()).isEqualTo(clothes1);
        assertThat(ccr.findById(2).get()).isEqualTo(clothes2);
        assertThat(ccr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        AvatarClothes clothes = ccr.save(AvatarClothes.builder().clothesNo(1).clothesUrl("oldUrl").build());
        assertThat(ccr.findById(1).get().getClothesUrl()).isEqualTo("oldUrl");

        clothes.setClothesUrl("newUrl");
        assertThat(ccr.findById(1).get().getClothesUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        AvatarClothes clothes = ccr.save(AvatarClothes.builder().clothesNo(1).clothesUrl("url").build());
        assertThat(ccr.findById(1).isPresent()).isEqualTo(true);

        ccr.delete(clothes);
        assertThat(ccr.findById(1).isPresent()).isEqualTo(false);
    }

}
