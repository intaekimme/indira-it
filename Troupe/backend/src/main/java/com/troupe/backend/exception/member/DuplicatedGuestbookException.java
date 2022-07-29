package com.troupe.backend.exception.member;

import com.troupe.backend.exception.DuplicatedException;

public class DuplicatedGuestbookException extends DuplicatedException {
    public DuplicatedGuestbookException() {
        super("이미 작성한 방명록이 존재합니다.\n");
    }
}
