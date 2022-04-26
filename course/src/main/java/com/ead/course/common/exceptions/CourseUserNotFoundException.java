package com.ead.course.common.exceptions;

public class CourseUserNotFoundException extends RuntimeException {

    public CourseUserNotFoundException(String msg){
        super(msg);
    }

}
