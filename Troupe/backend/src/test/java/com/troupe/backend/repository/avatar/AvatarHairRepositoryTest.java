package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarHair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarHairRepositoryTest {
    @Autowired
    AvatarHairRepository chr;

//    @Test
//    public void saveAndFindTest () {
//        AvatarHair hair1 = chr.save(AvatarHair.builder().hairNo(1).hairUrl("url1").build());
//        AvatarHair hair2 = chr.save(AvatarHair.builder().hairNo(2).hairUrl("url2").build());
//        AvatarHair hair3 = AvatarHair.builder().hairNo(3).hairUrl("url3").build();
//
//        assertThat(chr.findById(1).get()).isEqualTo(hair1);
//        assertThat(chr.findById(2).get()).isEqualTo(hair2);
////        assertThat(chr.findById(3).isPresent()).isEqualTo(false);
//    }
//
//    @Test
//    public void saveAndUpdateTest() {
//        AvatarHair hair = chr.save(AvatarHair.builder().hairNo(1).hairUrl("oldUrl").build());
//        assertThat(chr.findById(1).get().getHairUrl()).isEqualTo("oldUrl");
//
//        hair.setHairUrl("newUrl");
//        assertThat(chr.findById(1).get().getHairUrl()).isEqualTo("newUrl");
//    }
//
//    @Test
//    public void saveAndDeleteTest() {
//        AvatarHair hair = chr.save(AvatarHair.builder().hairNo(1).hairUrl("url").build());
//        assertThat(chr.findById(1).isPresent()).isEqualTo(true);
//
//        chr.delete(hair);
//        assertThat(chr.findById(1).isPresent()).isEqualTo(false);
//    }

}
