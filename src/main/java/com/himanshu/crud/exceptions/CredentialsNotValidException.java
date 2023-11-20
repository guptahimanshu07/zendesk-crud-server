package com.himanshu.crud.exceptions;

public class CredentialsNotValidException extends RuntimeException {

    public CredentialsNotValidException(String message) {
        super(message);
    }
}