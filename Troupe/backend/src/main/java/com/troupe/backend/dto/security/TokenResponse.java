package com.troupe.backend.dto.security;

import com.troupe.backend.dto.member.response.MemberInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private int memberNo;
    private String accessToken;
    private String refreshToken;
    private MemberInfoResponse memberInfoResponse;
}
