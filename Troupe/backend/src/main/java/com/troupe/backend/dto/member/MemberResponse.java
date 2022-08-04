package com.troupe.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    MemberInfoResponse memberInfoResponse;
    AvatarResponse avatarResponse;
}
