package com.troupe.backend.repository.character;

import com.troupe.backend.domain.character.CharacterHair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CharacterHairRepositoryTest {
    @Autowired
    CharacterHairRepository chr;

    @Test
    public void saveAndFindTest () {
        CharacterHair hair1 = chr.save(CharacterHair.builder().hairNo(1).hairUrl("url1").build());
        CharacterHair hair2 = chr.save(CharacterHair.builder().hairNo(2).hairUrl("url2").build());
        CharacterHair hair3 = CharacterHair.builder().hairNo(3).hairUrl("url3").build();

        assertThat(chr.findById(1).get()).isEqualTo(hair1);
        assertThat(chr.findById(2).get()).isEqualTo(hair2);
        assertThat(chr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        CharacterHair hair = chr.save(CharacterHair.builder().hairNo(1).hairUrl("oldUrl").build());
        assertThat(chr.findById(1).get().getHairUrl()).isEqualTo("oldUrl");

        hair.setHairUrl("newUrl");
        assertThat(chr.findById(1).get().getHairUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        CharacterHair hair = chr.save(CharacterHair.builder().hairNo(1).hairUrl("url").build());
        assertThat(chr.findById(1).isPresent()).isEqualTo(true);

        chr.delete(hair);
        assertThat(chr.findById(1).isPresent()).isEqualTo(false);
    }

}
