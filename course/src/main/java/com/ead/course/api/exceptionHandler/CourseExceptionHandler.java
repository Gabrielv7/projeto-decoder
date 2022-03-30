package com.ead.course.api.exceptionHandler;

import com.ead.course.domain.exceptions.CourseNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CourseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request){

        var status = HttpStatus.NOT_FOUND;

        var problem = createProblem(ex.getMessage(), status.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }


    private Problem createProblem(String message, Integer status){

        return Problem.builder()
                .message(message)
                .status(status)
                .timesTamp(LocalDateTime.now())
                .build();
    }


}
