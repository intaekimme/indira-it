package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarEye;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarEyeRepositoryTest {
    @Autowired
    AvatarEyeRepository cer;

    @Test
    public void saveAndFindTest () {
        AvatarEye eye1 = cer.save(AvatarEye.builder().eyeNo(1).eyeUrl("url1").build());
        AvatarEye eye2 = cer.save(AvatarEye.builder().eyeNo(2).eyeUrl("url2").build());
        AvatarEye eye3 = AvatarEye.builder().eyeNo(3).eyeUrl("url3").build();

        assertThat(cer.findById(1).get()).isEqualTo(eye1);
        assertThat(cer.findById(2).get()).isEqualTo(eye2);
        assertThat(cer.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        AvatarEye eye = cer.save(AvatarEye.builder().eyeNo(1).eyeUrl("oldUrl").build());
        assertThat(cer.findById(1).get().getEyeUrl()).isEqualTo("oldUrl");

        eye.setEyeUrl("newUrl");
        assertThat(cer.findById(1).get().getEyeUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        AvatarEye eye = cer.save(AvatarEye.builder().eyeNo(1).eyeUrl("url").build());
        assertThat(cer.findById(1).isPresent()).isEqualTo(true);

        cer.delete(eye);
        assertThat(cer.findById(1).isPresent()).isEqualTo(false);
    }

}
