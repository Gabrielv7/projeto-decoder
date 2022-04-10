package com.ead.course.common.exceptions;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String msg){
        super(msg);
    }

}
