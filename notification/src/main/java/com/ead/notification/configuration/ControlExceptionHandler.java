package com.ead.notification.configuration;

import com.ead.notification.exception.BusinessException;
import com.ead.notification.exception.NotFoundException;
import com.ead.notification.exception.StandadError;
import com.ead.notification.exception.StandadValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControlExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        StandadError standadError = buildErrorResponse(ex, BAD_REQUEST, request.getContextPath());
        return handleExceptionInternal(ex, standadError, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        StandadError standadError = buildErrorResponse(ex, NOT_FOUND, request.getContextPath());
        return handleExceptionInternal(ex, standadError, new HttpHeaders(), NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        StandadValidationError standadValidationError = new StandadValidationError(String.valueOf(LocalDateTime.now()), request.getContextPath(), BAD_REQUEST.value(),
                messageSource.getMessage("validation-error", null, LocaleContextHolder.getLocale()), messageSource.getMessage("request-invalid", null, LocaleContextHolder.getLocale()));

        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            standadValidationError.addError(field.getField(), field.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(standadValidationError);
    }

    private StandadError buildErrorResponse(Exception ex, HttpStatus status, String path) {
        return StandadError.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .path(path)
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

}
