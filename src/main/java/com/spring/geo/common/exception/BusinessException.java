package com.spring.geo.common.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private String status;

    public BusinessException(String message) {
        this(message, "fail");
    }

    public BusinessException(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return status;
    }

}