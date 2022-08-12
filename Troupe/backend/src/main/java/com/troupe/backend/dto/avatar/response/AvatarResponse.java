package com.troupe.backend.dto.avatar.response;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.util.MyConstant;
import lombok.Data;

@Data
public class AvatarResponse {
    String clothesUrl;
    String eyeUrl;
    String hairUrl;
    String mouthUrl;
    String noseUrl;
    String shapeUrl;

    public AvatarResponse(Member member) {
        this.clothesUrl = MyConstant.FILE_SERVER_URL + member.getClothes().getClothesUrl();
        this.eyeUrl = MyConstant.FILE_SERVER_URL + member.getEye().getEyeUrl();
        this.hairUrl = MyConstant.FILE_SERVER_URL + member.getHair().getHairUrl();
        this.mouthUrl = MyConstant.FILE_SERVER_URL + member.getMouth().getMouthUrl();
        this.noseUrl = MyConstant.FILE_SERVER_URL + member.getNose().getNoseUrl();
        this.shapeUrl = MyConstant.FILE_SERVER_URL + member.getShape().getShapeUrl();
    }
}
