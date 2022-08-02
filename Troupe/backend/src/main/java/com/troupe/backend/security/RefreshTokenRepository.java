package com.troupe.backend.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByMemberNo(int memberNo);
    void deleteByMemberNo(int memberNo);

}