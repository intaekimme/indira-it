package com.troupe.backend.dto.avatar.response;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.converter.AvatarConverter;
import com.troupe.backend.util.MyConstant;
import lombok.Data;

@Data
public class AvatarResponse {
    AvatarClothesResponse avatarClothesResponse;
    AvatarEyeResponse avatarEyeResponse;
    AvatarHairResponse avatarHairResponse;
    AvatarMouthResponse avatarMouthResponse;
    AvatarNoseResponse avatarNoseResponse;
    AvatarShapeResponse avatarShapeResponse;

    public AvatarResponse(Member member) {
        this.avatarClothesResponse = AvatarConverter.getAvatarClothesResponse(member.getClothes());
        this.avatarEyeResponse = AvatarConverter.getAvatarEyeResponse(member.getEye());
        this.avatarHairResponse = AvatarConverter.getAvatarHairResponse(member.getHair());
        this.avatarMouthResponse = AvatarConverter.getAvatarMouthResponse(member.getMouth());
        this.avatarNoseResponse = AvatarConverter.getAvatarNoseResponse(member.getNose());
        this.avatarShapeResponse = AvatarConverter.getAvatarShapeResponse(member.getShape());
    }
}
