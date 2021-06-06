package com.epam.esm.exception;

public class EntityExistsException extends RuntimeException{
    private final int code;

    public EntityExistsException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
