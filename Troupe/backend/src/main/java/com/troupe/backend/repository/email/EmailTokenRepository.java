package com.troupe.backend.repository.email;

import com.troupe.backend.domain.email.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    Optional<EmailToken> findByTokenAndExpirationDateAfterAndIsExpiredFalse(String token, LocalDateTime now);

    void deleteAllByEmail(String email);
}