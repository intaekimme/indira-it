package com.troupe.backend.exception.member;

import com.troupe.backend.exception.DuplicatedException;

public class DuplicatedFollowException extends DuplicatedException {
    public DuplicatedFollowException() {
        super("이미 팔로우 관계가 존재합니다.\n");
    }
}
