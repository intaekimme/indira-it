package com.troupe.backend.controller.email;

import com.troupe.backend.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/confirm-email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/{token}")
    public ResponseEntity confirmEmail(@Valid @PathVariable String token) {
        boolean result = emailService.verifyEmail(token);

        if (result) {
            return new ResponseEntity("이메일 인증 성공\n", HttpStatus.OK);
        } else {
            return new ResponseEntity("이메일 인증 실패\n", HttpStatus.UNAUTHORIZED);
        }
    }

}