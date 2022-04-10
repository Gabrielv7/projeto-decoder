package com.ead.authuser.common.exception;

public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException(String msg) {
        super(msg);
    }
}
