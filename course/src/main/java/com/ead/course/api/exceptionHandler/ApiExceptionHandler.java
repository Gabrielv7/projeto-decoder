package com.ead.course.api.exceptionHandler;

import com.ead.course.domain.exceptions.CourseNotFoundException;
import com.ead.course.domain.exceptions.CourseOrModuleNotFoundException;
import com.ead.course.domain.exceptions.LessonOrModuleNotFoundException;
import com.ead.course.domain.exceptions.ModuleNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request){

        log.error(ex.getMessage());

        var status = HttpStatus.NOT_FOUND;

        var problem = createProblem(ex.getMessage(), status.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<Object> handleModuleNotFoundException(ModuleNotFoundException ex, WebRequest request){

        log.error(ex.getMessage());

        var status = HttpStatus.NOT_FOUND;

        var problem = createProblem(ex.getMessage(), status.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }


    @ExceptionHandler(CourseOrModuleNotFoundException.class)
    public ResponseEntity<Object> handleCourseOrModuleNotFoundException(CourseOrModuleNotFoundException ex, WebRequest request){

        log.error(ex.getMessage());

        var status = HttpStatus.NOT_FOUND;

        var problem = createProblem(ex.getMessage(), status.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(LessonOrModuleNotFoundException.class)
    public ResponseEntity<Object> handleLessonOrModuleNotFoundException(LessonOrModuleNotFoundException ex, WebRequest request){

        log.error(ex.getMessage());

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
