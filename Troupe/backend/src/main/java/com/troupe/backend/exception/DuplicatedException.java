package com.troupe.backend.exception;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException(String s) {
        super(s);
    }
}
