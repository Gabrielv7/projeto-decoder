package com.ead.authuser.common.exceptionhandler;

import com.ead.authuser.common.exception.EmailExistsException;
import com.ead.authuser.common.exception.PasswordInvalidException;
import com.ead.authuser.common.exception.UserNameExistsException;
import com.ead.authuser.common.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Object> handleEmailExistsException(EmailExistsException ex, WebRequest request){

        log.error(ex.getMessage());

        var problem = createProblem(ex.getMessage(), HttpStatus.CONFLICT.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity<Object> handleUserNameExistsException(UserNameExistsException ex, WebRequest request){

        log.error(ex.getMessage());

        var problem = createProblem(ex.getMessage(), HttpStatus.CONFLICT.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){

        log.error(ex.getMessage());

        var problem = createProblem(ex.getMessage(), HttpStatus.NOT_FOUND.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<Object> handlePasswordInvalidException(PasswordInvalidException ex, WebRequest request){

        log.error(ex.getMessage());

        var problem = createProblem(ex.getMessage(), HttpStatus.CONFLICT.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);

    }


    private Problem createProblem(String msg, Integer status){

        return Problem.builder()
                .message(msg)
                .status(status)
                .timesTamp(LocalDateTime.now())
                .build();

    }

}
