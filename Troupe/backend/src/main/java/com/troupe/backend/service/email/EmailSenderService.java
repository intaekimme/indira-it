package com.troupe.backend.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    /**
     * 메일 전송 (비동기)
     */
    @Async
    public void sendEmail(SimpleMailMessage email) {
        System.out.println("이메일을 전송합니다.\n");
        javaMailSender.send(email);
    }

}
