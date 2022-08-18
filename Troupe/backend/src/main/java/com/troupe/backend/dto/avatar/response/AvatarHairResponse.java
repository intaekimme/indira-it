package com.troupe.backend.dto.avatar.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarHairResponse {
    int hairNo;
    String hairUrl;
}
