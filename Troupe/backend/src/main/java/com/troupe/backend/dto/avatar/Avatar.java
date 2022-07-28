package com.troupe.backend.dto.avatar;

import com.troupe.backend.domain.avatar.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 캐릭터의 눈/코/입 등의 각 요소들이 모여서 만드는 캐릭터 전체 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avatar {
    private AvatarClothes avatarClothes;
    private AvatarEye avatarEye;
    private AvatarHair avatarHair;
    private AvatarMouth avatarMouth;
    private AvatarNose avatarNose;
    private AvatarShape avatarShape;
}
