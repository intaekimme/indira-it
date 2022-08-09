package com.troupe.backend.service.email;

import com.troupe.backend.domain.email.EmailToken;
import com.troupe.backend.repository.email.EmailTokenRepository;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailTokenService {
    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;

    /**
     * 이메일 인증 토큰을 생성해서 DB에 저장하고 이메일을 보낸 후 토큰 리턴 (회원가입)
     */
    public String sendRegisterEmail(String receiverEmail) {
        // 이메일 토큰 생성
        EmailToken emailToken = EmailToken.createEmailToken(receiverEmail);

//        emailTokenRepository.deleteAllByEmail(receiverEmail);

        // DB에 이메일 토큰 저장
        emailTokenRepository.save(emailToken);

        // 이메일 작성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(MyConstant.EMAIL_SENDER_ADDRESS);
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/confirm-email/" + emailToken.getToken());

        // 이메일 전송
        emailSenderService.sendEmail(mailMessage);

        // 토큰 리턴
        return emailToken.getToken();
    }

    /**
     * 이메일 인증 토큰을 생성해서 DB에 저장하고 이메일을 보낸 후 토큰 리턴 (비밀번호 재설정)
     */
    public String sendResetPasswordEmail(String receiverEmail) {
        // 이메일 토큰 생성
        EmailToken emailToken = EmailToken.createEmailToken(receiverEmail);

//        emailTokenRepository.deleteAllByEmail(receiverEmail);

        // DB에 이메일 토큰 저장
        emailTokenRepository.save(emailToken);

        // 이메일 작성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(MyConstant.EMAIL_SENDER_ADDRESS);
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("비밀번호 재설정");
        mailMessage.setText("http://localhost:8080/member/reset-password/" + emailToken.getToken());

        // 이메일 전송
        System.out.println("비밀번호 재설정 이메일 전송");
        System.out.println(mailMessage.toString());
        emailSenderService.sendEmail(mailMessage);

        // 토큰 리턴
        return emailToken.getToken();
    }


    /**
     * token 스트링 값으로 유효한 emailToken 을 조회한다
     */
    public Optional<EmailToken> findValidEmailToken(String token) {
        return emailTokenRepository.findByTokenAndExpirationDateAfterAndIsExpiredFalse(token, LocalDateTime.now());
    }


    public void deleteAllByEmail(String email) {
        emailTokenRepository.deleteAllByEmail(email);
    }
}
