package com.vscg.samplestudentservice.exception;

import java.io.Serial;

public class InvalidDataException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidDataException(String message) {
        super(message);
    }
}