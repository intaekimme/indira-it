package com.troupe.backend.dto.avatar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/** 멤버가 아바타 수정 등을 요청했을 때 프론트로부터 받는 폼 */
public class AvatarForm {
    private int clothesNo;
    private int eyeNo;
    private int hairNo;
    private int mouthNo;
    private int noseNo;
    private int shapeNo;
}
