package com.troupe.backend.repository.avatar;

import com.troupe.backend.domain.avatar.AvatarShape;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarShapeRepositoryTest {
    @Autowired
    AvatarShapeRepository csr;

    @Test
    public void saveAndFindTest () {
        AvatarShape shape1 = csr.save(AvatarShape.builder().shapeNo(1).shapeUrl("url1").build());
        AvatarShape shape2 = csr.save(AvatarShape.builder().shapeNo(2).shapeUrl("url2").build());
        AvatarShape shape3 = AvatarShape.builder().shapeNo(3).shapeUrl("url3").build();

        assertThat(csr.findById(1).get()).isEqualTo(shape1);
        assertThat(csr.findById(2).get()).isEqualTo(shape2);
//        assertThat(csr.findById(3).isPresent()).isEqualTo(false);
    }

    @Test
    public void saveAndUpdateTest() {
        AvatarShape shape = csr.save(AvatarShape.builder().shapeNo(1).shapeUrl("oldUrl").build());
        assertThat(csr.findById(1).get().getShapeUrl()).isEqualTo("oldUrl");

        shape.setShapeUrl("newUrl");
        assertThat(csr.findById(1).get().getShapeUrl()).isEqualTo("newUrl");
    }

    @Test
    public void saveAndDeleteTest() {
        AvatarShape shape = csr.save(AvatarShape.builder().shapeNo(1).shapeUrl("url").build());
        assertThat(csr.findById(1).isPresent()).isEqualTo(true);

        csr.delete(shape);
        assertThat(csr.findById(1).isPresent()).isEqualTo(false);
    }

}
