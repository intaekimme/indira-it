package com.troupe.backend.repository.character;

import com.troupe.backend.domain.character.CharacterMouth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CharacterMouthRepositoryTest {
    @Autowired
    CharacterMouthRepository cmr;

    @Test
    public void saveAndFindTest () {
        CharacterMouth mouth1 = cmr.save(CharacterMouth.builder().mouthNo(1).mouthUrl("url1").build());
        CharacterMouth mouth2 = cmr.save(CharacterMouth.builder().mouthNo(2).mouthUrl("url2").build());
        CharacterMouth mouth3 = CharacterMouth.builder().mouthNo(3).mouthUrl("url3").build();

        assertThat(cmr.findById(1).get()).isEqualTo(mouth1);
        assertThat(cmr.findById(2).get()).isEqualTo(mouth2);
        assertThat(cmr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        CharacterMouth mouth = cmr.save(CharacterMouth.builder().mouthNo(1).mouthUrl("oldUrl").build());
        assertThat(cmr.findById(1).get().getMouthUrl()).isEqualTo("oldUrl");

        mouth.setMouthUrl("newUrl");
        assertThat(cmr.findById(1).get().getMouthUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        CharacterMouth mouth = cmr.save(CharacterMouth.builder().mouthNo(1).mouthUrl("url").build());
        assertThat(cmr.findById(1).isPresent()).isEqualTo(true);

        cmr.delete(mouth);
        assertThat(cmr.findById(1).isPresent()).isEqualTo(false);
    }

}
