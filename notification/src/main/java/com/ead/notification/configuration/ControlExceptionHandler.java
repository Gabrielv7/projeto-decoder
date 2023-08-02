package com.ead.notification.configuration;

import com.ead.notification.dto.response.ErrorResponseCustomized;
import com.ead.notification.dto.response.ErrorResponse;
import com.ead.notification.exception.BusinessException;
import com.ead.notification.exception.FieldError;
import com.ead.notification.exception.NotFoundException;
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
        List<FieldError> errors = getFieldErrors(ex);
        ErrorResponseCustomized errorResponse = buildErrorsResponse(status, errors);
        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private List<FieldError> getFieldErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getDefaultMessage(), error.getField()))
                .toList();
    }

    private ErrorResponseCustomized buildErrorsResponse(HttpStatus status, List<FieldError> errors) {
        String message = messageSource.getMessage("request.invalid", null, LocaleContextHolder.getLocale());
        return new ErrorResponseCustomized(status.value(), message, errors);
    }

    private ErrorResponse buildErrorResponse(Exception ex, HttpStatus status) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }

}
