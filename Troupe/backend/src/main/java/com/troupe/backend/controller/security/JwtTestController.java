package com.troupe.backend.controller.security;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@Api("리프레시 토큰 REST API")
@RequiredArgsConstructor
@RestController
public class JwtTestController {

    @PostMapping("/test")
    public String test(){
        return "<h1>test passed</h1>";
    }
}