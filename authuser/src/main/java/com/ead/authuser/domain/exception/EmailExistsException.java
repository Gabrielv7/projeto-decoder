package com.ead.authuser.domain.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String msg) {
        super(msg);
    }
}
