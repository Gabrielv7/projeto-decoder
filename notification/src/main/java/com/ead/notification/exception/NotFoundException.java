package com.ead.notification.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String mensagem){
        super(mensagem);
    }

}
