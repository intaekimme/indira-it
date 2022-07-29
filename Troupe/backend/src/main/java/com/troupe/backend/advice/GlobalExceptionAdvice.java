package com.troupe.backend.advice;

import com.troupe.backend.exception.DuplicatedException;
import com.troupe.backend.util.MyConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException e) {
        e.printStackTrace();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MESSAGE, e.getMessage());
        return new ResponseEntity(resultMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity handleDuplicateException(DuplicatedException e) {
        e.printStackTrace();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MESSAGE, e.getMessage());
        return new ResponseEntity(resultMap, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleDuplicateMemberException(Exception e) {
        e.printStackTrace();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(MyConstant.MESSAGE, e.getMessage());
        return new ResponseEntity(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}