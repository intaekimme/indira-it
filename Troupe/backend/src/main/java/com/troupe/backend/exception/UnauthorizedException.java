package com.troupe.backend.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("인증 정보가 잘못되었습니다.\n");
    }
}
