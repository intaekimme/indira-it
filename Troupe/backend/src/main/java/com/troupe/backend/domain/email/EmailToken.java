package com.troupe.backend.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_email_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailToken {
    private static final long EMAIL_TOKEN_AVAILABLE_MINUTE = 180L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String token;

    private String email;

    private boolean isExpired;

    private LocalDateTime expirationDate;

    // 이메일 인증 토큰 생성
    public static EmailToken createEmailToken(String email) {
        EmailToken emailToken = EmailToken.builder()
                .email(email)
                .expirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_AVAILABLE_MINUTE))
                .isExpired(false)
                .build();

        return emailToken;
    }

    // 토큰 만료
    public void setTokenToUsed() {
        this.isExpired = true;
    }
}