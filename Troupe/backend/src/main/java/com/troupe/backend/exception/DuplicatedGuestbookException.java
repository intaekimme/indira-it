package com.troupe.backend.exception;

public class DuplicatedGuestbookException extends DuplicatedException {
    public DuplicatedGuestbookException() {
        super("이미 작성한 방명록이 존재합니다.\n");
    }
}
