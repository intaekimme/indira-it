package com.troupe.backend.dto.avatar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarClothesForm {
    int clothesNo;
    MultipartFile clothesImage;
}
