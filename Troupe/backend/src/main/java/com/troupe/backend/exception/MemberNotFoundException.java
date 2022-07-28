package com.troupe.backend.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message){
        super(message);
    }
}
