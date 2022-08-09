package com.troupe.backend.dto.member.response;

import com.troupe.backend.dto.avatar.response.AvatarResponse;
import com.troupe.backend.dto.likability.LikabilityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberWithLikabilityResponse {
    MemberInfoResponse memberInfoResponse;
    AvatarResponse avatarResponse;
    LikabilityResponse likabilityResponse;
}
