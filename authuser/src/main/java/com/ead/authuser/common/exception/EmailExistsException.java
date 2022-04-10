package com.ead.authuser.common.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String msg) {
        super(msg);
    }
}
