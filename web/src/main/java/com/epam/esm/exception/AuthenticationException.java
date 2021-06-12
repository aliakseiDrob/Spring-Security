package com.epam.esm.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
}
