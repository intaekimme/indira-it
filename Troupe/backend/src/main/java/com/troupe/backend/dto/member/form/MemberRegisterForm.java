package com.troupe.backend.dto.member.form;

import com.troupe.backend.domain.member.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRegisterForm {
    private String email;
    private String password;
    private String nickname;
    private String description;
    private MemberType memberType;
    private MultipartFile profileImage;

}
