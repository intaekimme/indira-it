package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.dto.avatar.response.*;
import com.troupe.backend.util.MyConstant;

import java.util.ArrayList;
import java.util.List;

public class AvatarConverter {
    public static AvatarClothesResponse getAvatarClothesResponse (AvatarClothes avatarClothes) {
        return AvatarClothesResponse.builder()
                .clothesNo(avatarClothes.getClothesNo())
                .clothesUrl(MyConstant.FILE_SERVER_URL + avatarClothes.getClothesUrl())
                .build();
    }

    public static AvatarEyeResponse getAvatarEyeResponse (AvatarEye avatarEye) {
        return AvatarEyeResponse.builder()
                .eyeNo(avatarEye.getEyeNo())
                .eyeUrl(MyConstant.FILE_SERVER_URL + avatarEye.getEyeUrl())
                .build();
    }

    public static AvatarHairResponse getAvatarHairResponse (AvatarHair avatarHair) {
        return AvatarHairResponse.builder()
                .hairNo(avatarHair.getHairNo())
                .hairUrl(MyConstant.FILE_SERVER_URL + avatarHair.getHairUrl())
                .build();
    }

    public static AvatarMouthResponse getAvatarMouthResponse (AvatarMouth avatarMouth) {
        return AvatarMouthResponse.builder()
                .mouthNo(avatarMouth.getMouthNo())
                .mouthUrl(MyConstant.FILE_SERVER_URL + avatarMouth.getMouthUrl())
                .build();
    }

    public static AvatarNoseResponse getAvatarNoseResponse (AvatarNose avatarNose) {
        return AvatarNoseResponse.builder()
                .noseNo(avatarNose.getNoseNo())
                .noseUrl(MyConstant.FILE_SERVER_URL + avatarNose.getNoseUrl())
                .build();
    }

    public static AvatarShapeResponse getAvatarShapeResponse (AvatarShape avatarShape) {
        return AvatarShapeResponse.builder()
                .shapeNo(avatarShape.getShapeNo())
                .shapeUrl(MyConstant.FILE_SERVER_URL + avatarShape.getShapeUrl())
                .build();
    }

    public static List<AvatarClothesResponse> getAvatarClothesResponseList (List<AvatarClothes> list) {
        List<AvatarClothesResponse> ret = new ArrayList<>();

        for (AvatarClothes avatarClothes : list) {
            AvatarClothesResponse avatarClothesResponse = getAvatarClothesResponse(avatarClothes);
            ret.add(avatarClothesResponse);
        }

        return ret;
    }

    public static List<AvatarEyeResponse> getAvatarEyeResponseList (List<AvatarEye> list) {
        List<AvatarEyeResponse> ret = new ArrayList<>();

        for (AvatarEye avatarEye : list) {
            AvatarEyeResponse avatarEyeResponse = getAvatarEyeResponse(avatarEye);
            ret.add(avatarEyeResponse);
        }

        return ret;
    }

    public static List<AvatarHairResponse> getAvatarHairResponseList (List<AvatarHair> list) {
        List<AvatarHairResponse> ret = new ArrayList<>();

        for (AvatarHair avatarHair : list) {
            AvatarHairResponse avatarHairResponse = getAvatarHairResponse(avatarHair);
            ret.add(avatarHairResponse);
        }

        return ret;
    }

    public static List<AvatarMouthResponse> getAvatarMouthResponseList (List<AvatarMouth> list) {
        List<AvatarMouthResponse> ret = new ArrayList<>();

        for (AvatarMouth avatarMouth : list) {
            AvatarMouthResponse avatarMouthResponse = getAvatarMouthResponse(avatarMouth);
            ret.add(avatarMouthResponse);
        }

        return ret;
    }

    public static List<AvatarNoseResponse> getAvatarNoseResponseList (List<AvatarNose> list) {
        List<AvatarNoseResponse> ret = new ArrayList<>();

        for (AvatarNose avatarNose : list) {
            AvatarNoseResponse avatarNoseResponse = getAvatarNoseResponse(avatarNose);
            ret.add(avatarNoseResponse);
        }

        return ret;
    }

    public static List<AvatarShapeResponse> getAvatarShapeResponseList (List<AvatarShape> list) {
        List<AvatarShapeResponse> ret = new ArrayList<>();

        for (AvatarShape avatarShape : list) {
            AvatarShapeResponse avatarShapeResponse = getAvatarShapeResponse(avatarShape);
            ret.add(avatarShapeResponse);
        }

        return ret;
    }

}
