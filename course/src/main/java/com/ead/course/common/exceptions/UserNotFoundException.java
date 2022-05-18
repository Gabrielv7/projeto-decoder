package com.ead.course.common.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg){
        super(msg);
    }

}
