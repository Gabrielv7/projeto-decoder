package com.ead.course.domain.assembler;

import com.ead.course.domain.Course;
import com.ead.course.domain.CourseUser;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CourseUserAssembler {
    public CourseUser assemblerCourseUserBeforeSave(Course course, UUID userId){
      return  CourseUser.builder()
                .course(course)
                .userId(userId)
                .build();
    }
}
