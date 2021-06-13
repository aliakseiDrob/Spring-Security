package com.epam.esm.exception;

public class AccountEntityException extends RuntimeException {
    private final int code;

    public AccountEntityException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
