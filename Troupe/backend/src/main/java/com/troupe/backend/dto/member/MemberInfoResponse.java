package com.troupe.backend.dto.member;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import lombok.Data;

@Data
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
        this.profileImageUrl = member.getProfileImageUrl();
        this.isRemoved = member.isRemoved();
    }
}
