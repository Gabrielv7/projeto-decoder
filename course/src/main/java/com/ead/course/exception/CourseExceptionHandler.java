package com.ead.course.exception;

import com.ead.course.domain.dto.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CourseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, HttpStatus.BAD_REQUEST, null);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, HttpStatus.NOT_FOUND, null);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorObject> errors = getFieldErrors(ex);
        ErrorResponse errorResponse = buildErrorResponse(ex, status, errors);
        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private List<ErrorObject> getFieldErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField()))
                .collect(Collectors.toList());
    }

    private ErrorResponse buildErrorResponse(Exception ex, HttpStatus status, List<ErrorObject> errors) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode((status.value()));
        if(ex instanceof MethodArgumentNotValidException){
            errorResponse.setMessage(messageSource.getMessage("request-invalid", null, LocaleContextHolder.getLocale()));
            if(Objects.nonNull(errors)){
                errorResponse.setErrors(errors);
            }
        }else{
            errorResponse.setMessage(ex.getMessage());
        }
        return errorResponse;
    }


}
