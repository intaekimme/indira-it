package com.troupe.backend.dto.avatar;

import com.troupe.backend.domain.avatar.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 아바타의 옷/눈/머리카락/입/코/얼굴형 등의 요소들이 모여서 만드는 아바타 전체 */
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
