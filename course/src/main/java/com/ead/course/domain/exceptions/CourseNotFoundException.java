package com.ead.course.domain.exceptions;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String msg){
        super(msg);
    }

}
