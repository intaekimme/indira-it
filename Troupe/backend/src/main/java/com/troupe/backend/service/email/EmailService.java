package com.troupe.backend.service.email;

import com.troupe.backend.domain.email.EmailToken;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    private final EmailTokenService emailTokenService;

    private final MemberRepository memberRepository;

    /**
     * 이메일 인증하고, 성공 시 인증 여부 기록
     */
    public boolean verifyEmail(String token) {
        // 유효한 이메일 토큰을 조회
        Optional<EmailToken> foundEmailToken = emailTokenService.findValidEmailToken(token);

        // 유효한 토큰이 있는 경우
        if (foundEmailToken.isPresent()) {
            EmailToken emailToken = foundEmailToken.get();

            // 인증 여부 true 로 변경
            Member foundMember=  memberRepository.findByEmail(emailToken.getEmail()).get();
            foundMember.setAuthenticatedEmail(true);

            // 토큰 사용
            emailToken.setTokenToUsed();

            return true;
        } else {
            return false;
        }
    }

}