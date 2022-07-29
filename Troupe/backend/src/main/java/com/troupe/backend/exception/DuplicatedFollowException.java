package com.troupe.backend.exception;

public class DuplicatedFollowException extends DuplicatedException {
    public DuplicatedFollowException() {
        super("이미 팔로우 관계가 존재합니다.\n");
    }
}
