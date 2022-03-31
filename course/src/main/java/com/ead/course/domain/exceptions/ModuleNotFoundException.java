package com.ead.course.domain.exceptions;

public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(String msg){
        super(msg);
    }

}
