package com.troupe.backend.advice;

import com.troupe.backend.exception.DuplicateMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity handleDuplicateMemberException(DuplicateMemberException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e, HttpStatus.CONFLICT);
    }

}