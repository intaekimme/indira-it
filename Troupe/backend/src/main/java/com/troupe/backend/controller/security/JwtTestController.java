package com.troupe.backend.controller.security;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@Api("리프레시 토큰 REST API")
@RequiredArgsConstructor
@RestController
public class JwtTestController {

    @Operation(summary = "jwt 토큰 인증 잘 되는지 확인", description = "파라미터 : accessToken (리퀘스트헤더) ")
    @PostMapping("/test")
    public String test(){
        return "<h1>test passed</h1>";
    }
}