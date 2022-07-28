package com.troupe.backend.dto.member;

import com.troupe.backend.domain.member.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberForm {
    private String email;
    private String password;
    private String nickname;
    private String description;
    private MemberType memberType;
    private File profileImage;
}
