package com.troupe.backend.exception.performance;

public class PerformanceNotFoundException extends RuntimeException{
    public PerformanceNotFoundException(String message){
        super(message);
    }
}
