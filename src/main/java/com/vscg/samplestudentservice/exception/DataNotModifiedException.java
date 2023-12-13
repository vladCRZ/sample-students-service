package com.vscg.samplestudentservice.exception;

import java.io.Serial;

public class DataNotModifiedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DataNotModifiedException(String message) {
        super(message);
    }
}
