package com.ead.course.service;

import com.ead.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;


public interface CourseService {

    void delete(UUID courseId);

    Course save(Course course);

    Course findById(UUID courseId);

    Course update(UUID courseId, Course course);

    Page<Course> decideWhichSpecToCall(UUID userId, Specification<Course> spec, Pageable pageable);

    void saveSubscriptionUserInCourse(UUID courseId, UUID courseID);

}
