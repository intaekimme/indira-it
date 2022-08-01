package com.troupe.backend.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtTestController {

    @PostMapping("/test")
    public String test(){
        return "<h1>test passed</h1>";
    }
}