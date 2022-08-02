package com.troupe.backend.domain.security;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenNo;

    private String refreshToken;

    private Integer memberNo;
}
