package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarNose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarNoseRepositoryTest {
    @Autowired
    AvatarNoseRepository cnr;

    @Test
    public void saveAndFindTest () {
        AvatarNose nose1 = cnr.save(AvatarNose.builder().noseNo(1).noseUrl("url1").build());
        AvatarNose nose2 = cnr.save(AvatarNose.builder().noseNo(2).noseUrl("url2").build());
        AvatarNose nose3 = AvatarNose.builder().noseNo(3).noseUrl("url3").build();

        assertThat(cnr.findById(1).get()).isEqualTo(nose1);
        assertThat(cnr.findById(2).get()).isEqualTo(nose2);
        assertThat(cnr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        AvatarNose nose = cnr.save(AvatarNose.builder().noseNo(1).noseUrl("oldUrl").build());
        assertThat(cnr.findById(1).get().getNoseUrl()).isEqualTo("oldUrl");

        nose.setNoseUrl("newUrl");
        assertThat(cnr.findById(1).get().getNoseUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        AvatarNose nose = cnr.save(AvatarNose.builder().noseNo(1).noseUrl("url").build());
        assertThat(cnr.findById(1).isPresent()).isEqualTo(true);

        cnr.delete(nose);
        assertThat(cnr.findById(1).isPresent()).isEqualTo(false);
    }

}
