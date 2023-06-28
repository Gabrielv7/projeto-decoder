package com.ead.course.configuration;

import com.ead.course.domain.dto.response.ErrorResponse;
import com.ead.course.exception.BusinessException;
import com.ead.course.exception.ErrorObject;
import com.ead.course.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControlExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpStatus STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus STATUS_NOT_FOUND = HttpStatus.NOT_FOUND;

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, STATUS_BAD_REQUEST);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), STATUS_BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, STATUS_NOT_FOUND);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), STATUS_NOT_FOUND, request);
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
        errorResponse.setMessage(messageSource.getMessage("request.invalid", null, LocaleContextHolder.getLocale()));
        if(!errors.isEmpty()){
            errorResponse.setErrors(errors);
        }
        return errorResponse;
    }

    private ErrorResponse buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode((status.value()));
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

}
