package com.troupe.backend.service.security;

import com.troupe.backend.repository.security.RefreshTokenRepository;
import com.troupe.backend.domain.security.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 새로운 리프레시 토큰을 DB 에 저장
     */
    public void saveRefreshToken(int memberNo, String refreshToken) {
        RefreshToken refreshTokenObject = RefreshToken.builder().memberNo(memberNo).refreshToken(refreshToken).build();

        // 기존 리프레시 토큰이 DB에 존재하면 제거
        if (refreshTokenRepository.existsByMemberNo(memberNo)) {
            refreshTokenRepository.deleteByMemberNo(memberNo);
        }

        // 새로운 리프레시 토큰 저장
        refreshTokenRepository.save(refreshTokenObject);
    }

    /**
     * DB에서 리프레시 토큰을 조회
     */
    public Optional<RefreshToken> findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

}
