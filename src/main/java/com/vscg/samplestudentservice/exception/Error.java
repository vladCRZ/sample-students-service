package com.vscg.samplestudentservice.exception;

import lombok.Data;

@Data
public class Error {
    private String message;
    private int status;
    private Long timestamp;
}