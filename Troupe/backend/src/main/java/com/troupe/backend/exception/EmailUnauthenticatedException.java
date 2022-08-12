package com.troupe.backend.exception;

public class EmailUnauthenticatedException extends RuntimeException {
    public EmailUnauthenticatedException() {
        super("인증되지 않은 이메일입니다.\n");
    }
}
