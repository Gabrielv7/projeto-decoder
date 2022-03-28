package com.ead.authuser.domain.exception;

public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException(String msg) {
        super(msg);
    }
}
