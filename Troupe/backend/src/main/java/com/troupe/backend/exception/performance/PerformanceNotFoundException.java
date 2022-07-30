package com.troupe.backend.exception.performance;

import java.util.NoSuchElementException;

public class PerformanceNotFoundException extends NoSuchElementException {
    public PerformanceNotFoundException(String message){
        super(message);
    }
}
