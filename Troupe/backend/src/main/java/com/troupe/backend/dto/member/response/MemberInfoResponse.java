package com.troupe.backend.dto.member.response;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import com.troupe.backend.util.MyConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoResponse {
    int memberNo;
    String email;
    String nickname;
    String description;
    MemberType memberType;
    String profileImageUrl;
    boolean isRemoved;

    public MemberInfoResponse(Member member) {
        this.memberNo = member.getMemberNo();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.description = member.getDescription();
        this.memberType = member.getMemberType();
        this.profileImageUrl = MyConstant.FILE_SERVER_URL + member.getProfileImageUrl();
        this.isRemoved = member.isRemoved();
    }
}
