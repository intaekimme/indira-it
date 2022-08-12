package com.troupe.backend.exception.member;

import com.troupe.backend.exception.DuplicatedException;

public class DuplicatedMemberException extends DuplicatedException {
    public DuplicatedMemberException() {
        super("이미 회원 정보가 존재합니다.\n");
    }
}

