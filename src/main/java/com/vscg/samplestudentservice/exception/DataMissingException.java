package com.vscg.samplestudentservice.exception;

import java.io.Serial;

public class DataMissingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DataMissingException(String message) {
        super(message);
    }

}