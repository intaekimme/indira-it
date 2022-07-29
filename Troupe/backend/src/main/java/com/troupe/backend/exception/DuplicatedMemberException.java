package com.troupe.backend.exception;

public class DuplicatedMemberException extends DuplicatedException {
    public DuplicatedMemberException() {
        super("이미 회원 정보가 존재합니다.\n");
    }
}

