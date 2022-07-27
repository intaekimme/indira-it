package com.troupe.backend.repository.character;

import com.troupe.backend.domain.character.CharacterNose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CharacterNoseRepositoryTest {
    @Autowired
    CharacterNoseRepository cnr;

    @Test
    public void saveAndFindTest () {
        CharacterNose nose1 = cnr.save(CharacterNose.builder().noseNo(1).noseUrl("url1").build());
        CharacterNose nose2 = cnr.save(CharacterNose.builder().noseNo(2).noseUrl("url2").build());
        CharacterNose nose3 = CharacterNose.builder().noseNo(3).noseUrl("url3").build();

        assertThat(cnr.findById(1).get()).isEqualTo(nose1);
        assertThat(cnr.findById(2).get()).isEqualTo(nose2);
        assertThat(cnr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        CharacterNose nose = cnr.save(CharacterNose.builder().noseNo(1).noseUrl("oldUrl").build());
        assertThat(cnr.findById(1).get().getNoseUrl()).isEqualTo("oldUrl");

        nose.setNoseUrl("newUrl");
        assertThat(cnr.findById(1).get().getNoseUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        CharacterNose nose = cnr.save(CharacterNose.builder().noseNo(1).noseUrl("url").build());
        assertThat(cnr.findById(1).isPresent()).isEqualTo(true);

        cnr.delete(nose);
        assertThat(cnr.findById(1).isPresent()).isEqualTo(false);
    }

}
