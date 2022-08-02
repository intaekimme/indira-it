package com.troupe.backend.dto.avatar.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarNoseForm {
    int noseNo;
    MultipartFile noseImage;
}
