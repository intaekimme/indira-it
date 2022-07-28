package com.troupe.backend.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super("이미 DB에 회원 정보가 존재합니다.\n");
    }
}
